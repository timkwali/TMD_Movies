package com.timkwali.tmdmovies.movieslist.presenter

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.timkwali.tmdmovies.R
import com.timkwali.tmdmovies.common.data.model.genres.Genre
import com.timkwali.tmdmovies.common.utils.Constants
import com.timkwali.tmdmovies.common.utils.MovieType
import com.timkwali.tmdmovies.common.utils.OnItemClick
import com.timkwali.tmdmovies.common.utils.Resource
import com.timkwali.tmdmovies.common.utils.Utils.gone
import com.timkwali.tmdmovies.common.utils.Utils.showSnackBar
import com.timkwali.tmdmovies.common.utils.Utils.visible
import com.timkwali.tmdmovies.databinding.FragmentMoviesListBinding
import com.timkwali.tmdmovies.common.domain.model.Movie
import com.timkwali.tmdmovies.common.utils.Utils.getGenreNameFromId
import com.timkwali.tmdmovies.movieslist.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesListFragment : Fragment(), OnItemClick<Movie> {

    private var _binding: FragmentMoviesListBinding? = null
    private val binding: FragmentMoviesListBinding get() = _binding!!
    private lateinit var movieType: MovieType
    private lateinit var genres: MutableList<Genre>
    private lateinit var adapter: MoviesListRvAdapter
    private lateinit var pageTitle: String
    private val moviesListViewModel: MoviesListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieType = arguments?.get(Constants.MOVIE_TYPE_BUNDLE_KEY) as MovieType
        genres = arguments?.get(Constants.GENRES_BUNDLE_KEY) as MutableList<Genre>
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        setHasOptionsMenu(true)

        binding.apply {
            pageTitle = when(movieType) {
                MovieType.LATEST -> getString(R.string.latest_movies)
                MovieType.POPULAR -> getString(R.string.popular_movies)
                MovieType.UPCOMING -> getString(R.string.upcoming_movies)
            }
            moviesListViewModel.apply {
                getMovies(movieType)
                moviesList.observe(viewLifecycleOwner, { handleResponse(it) })
                moviesGenres.observe(viewLifecycleOwner, { resource ->
                    if(resource is Resource.Error) showSnackBar(resource.message)
                    resource.data.let { genreList ->
                        genres.clear()
                        genreList?.forEach { genres.add(it) }
                    }
                })
            }

            setupToolbar(toolbar, pageTitle)
            moviesListRetryTv.setOnClickListener { refresh() }
        }
    }

    private fun handleResponse(resource: Resource<List<Movie>>) {
        binding.apply {
            resource.data?.let { it -> setUpRecyclerView(it) }
            Utils.setUpSearchView(searchSv, adapter)
            when(resource) {
                is Resource.Loading -> {
                    moviesListRetryLl.gone()
                    moviesListPb.isVisible = moviesListRv.adapter?.itemCount == 0
                }
                is Resource.Success -> {
                    moviesListRetryLl.gone()
                    moviesListPb.gone()
                }
                is Resource.Error -> {
                    if(resource.data?.isNotEmpty() == true) moviesListRetryLl.gone()
                    if(moviesListRv.adapter?.itemCount == 0) {
                        moviesListRetryLl.visible()
                        moviesListErrorTv.text = resource.message
                    } else showSnackBar(resource.message)
                    moviesListPb.gone()
                    Log.d("errorResponse", resource.message.toString())
                }
            }
        }
    }

    private fun setUpRecyclerView(moviesList: List<Movie>) {
        adapter = MoviesListRvAdapter(moviesList, genres, this)
        binding.apply {
            moviesListRv.adapter = adapter
            moviesListRv.layoutManager = LinearLayoutManager(requireContext())
            moviesListRv.setHasFixedSize(true)
        }
    }

    override fun onItemClick(item: Movie, position: Int) {
        val genreNames = item.genres?.let {
            getGenreNameFromId(it, genres)
        }
        val bundle = bundleOf(
            Constants.MOVIE_BUNDLE_KEY to item,
            Constants.GENRES_BUNDLE_KEY to genreNames
        )
        findNavController().navigate(R.id.movieDetailsFragment, bundle)
    }

    private fun setupToolbar(toolbar: Toolbar, title: String) {
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
        val actionBar: ActionBar? = (activity as AppCompatActivity?)?.supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_round_arrow_back_24)
            toolbar.setNavigationOnClickListener { v: View? -> findNavController().popBackStack() }
            toolbar.setTitleTextColor(resources.getColor(R.color.black))
            actionBar.title = title
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_items, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.menu_refresh) {
            refresh()
            true
        } else {
            super.onOptionsItemSelected(item)
            false
        }
    }

    private fun refresh() {
        moviesListViewModel.apply {
            getMoviesGenres()
            getMovies(movieType)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}