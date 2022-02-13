package com.timkwali.tmdmovies.moviescategories.presentation

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.timkwali.tmdmovies.R
import com.timkwali.tmdmovies.common.data.model.genres.Genre
import com.timkwali.tmdmovies.common.domain.model.Movie
import com.timkwali.tmdmovies.common.utils.Constants
import com.timkwali.tmdmovies.common.utils.MovieType
import com.timkwali.tmdmovies.common.utils.OnItemClick
import com.timkwali.tmdmovies.common.utils.Resource
import com.timkwali.tmdmovies.common.utils.Utils.getGenreNameFromId
import com.timkwali.tmdmovies.common.utils.Utils.gone
import com.timkwali.tmdmovies.common.utils.Utils.showSnackBar
import com.timkwali.tmdmovies.common.utils.Utils.visible
import com.timkwali.tmdmovies.databinding.FragmentMoviesCategoriesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesCategoriesFragment : Fragment(), OnItemClick<Movie> {

    private var _binding: FragmentMoviesCategoriesBinding? = null
    private val binding get() = _binding!!
    private val moviesCategoriesViewModel: MoviesCategoriesViewModel by viewModels()
    private var genres = mutableListOf<Genre>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMoviesCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val toolbar: Toolbar = view.findViewById(R.id.toolbar)
            setupToolbar(toolbar)
            setClickListeners()
            setHasOptionsMenu(true)
            moviesCategoriesViewModel.apply {
                latestMovies.observe(viewLifecycleOwner, {
                    handleResponse(it, latestMoviesRv, latestRetryLl, latestErrorTv, latestPb)
                })
                popularMovies.observe(viewLifecycleOwner, {
                    handleResponse(it, popularMoviesRv, popularRetryLl, popularErrorTv, popularPb)
                })
                upcomingMovies.observe(viewLifecycleOwner, {
                    handleResponse(it, upcomingMoviesRv, upcomingRetryLl, upcomingErrorTv, upcomingPb)
                })
                moviesGenres.observe(viewLifecycleOwner, { resource ->
                    if(resource is Resource.Error) showSnackBar(resource.message)
                    resource.data.let { genreList ->
                        genres.clear()
                        genreList?.forEach { genres.add(it) }
                    }
                })
            }
        }
    }

    private fun handleResponse(
        resource: Resource<List<Movie>>,
        recyclerView: RecyclerView,
        retry: LinearLayout,
        errorText: TextView,
        progressBar: ProgressBar
    ) {
        resource.data?.let { it -> setUpRecyclerView(recyclerView, it) }
        when(resource) {
            is Resource.Loading -> {
                retry.gone()
                progressBar.isVisible = recyclerView.adapter?.itemCount == 0
            }
            is Resource.Success -> {
                retry.gone()
                progressBar.gone()
            }
            is Resource.Error -> {
                if(resource.data?.isNotEmpty() == true) retry.gone()
                if(recyclerView.adapter?.itemCount == 0) {
                    retry.visible()
                    errorText.text = resource.message
                } else showSnackBar(resource.message)
                progressBar.gone()
                Log.d("errorResponse", resource.message.toString())
            }
        }
    }

    private fun setUpRecyclerView(recyclerView: RecyclerView, moviesList: List<Movie>) {
        val adapter = MovieCategoryRvAdapter(moviesList, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false)
        recyclerView.setHasFixedSize(true)
    }

    private fun setClickListeners() {
        binding.apply {
            latestRetryTv.setOnClickListener { moviesCategoriesViewModel.getLatestMovies() }
            popularRetryTv.setOnClickListener { moviesCategoriesViewModel.getPopularMovies() }
            upcomingRetryTv.setOnClickListener { moviesCategoriesViewModel.getUpcomingMovies() }
            latestMoviesMoreBtn.setOnClickListener { goToMore(MovieType.LATEST) }
            popularMoviesMoreBtn.setOnClickListener { goToMore(MovieType.POPULAR) }
            upcomingMoviesMoreBtn.setOnClickListener { goToMore(MovieType.UPCOMING) }
        }
    }

    private fun goToMore(movieType: MovieType) {
        val bundle = bundleOf(
            Constants.MOVIE_TYPE_BUNDLE_KEY to movieType,
            Constants.GENRES_BUNDLE_KEY to genres
        )
        findNavController().navigate(R.id.moviesListFragment, bundle)
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

    private fun setupToolbar(toolbar: Toolbar) {
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
        val actionBar: ActionBar? = (activity as AppCompatActivity?)?.supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false)
            toolbar.setTitleTextColor(resources.getColor(R.color.black))
            val title = requireActivity().getString(R.string.app_name)
            actionBar.title = title
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_items, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.menu_refresh) {
            moviesCategoriesViewModel.apply {
                getLatestMovies()
                getPopularMovies()
                getUpcomingMovies()
                getMoviesGenres()
            }
            true
        } else {
            super.onOptionsItemSelected(item)
            false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}