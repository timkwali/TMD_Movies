package com.timkwali.tmdmovies.movieslist.presenter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.timkwali.tmdmovies.R
import com.timkwali.tmdmovies.common.data.model.genres.Genre
import com.timkwali.tmdmovies.common.presentation.MoviesViewModel
import com.timkwali.tmdmovies.common.utils.Constants
import com.timkwali.tmdmovies.common.utils.MovieType
import com.timkwali.tmdmovies.common.utils.OnItemClick
import com.timkwali.tmdmovies.common.utils.Resource
import com.timkwali.tmdmovies.common.utils.Utils.gone
import com.timkwali.tmdmovies.common.utils.Utils.showSnackBar
import com.timkwali.tmdmovies.common.utils.Utils.visible
import com.timkwali.tmdmovies.databinding.FragmentMoviesListBinding
import com.timkwali.tmdmovies.moviescategories.domain.model.Movie
import com.timkwali.tmdmovies.moviescategories.presentation.MovieCategoryRvAdapter
import com.timkwali.tmdmovies.movieslist.utils.Utils

class MoviesListFragment : Fragment(), OnItemClick<Movie> {

    private var _binding: FragmentMoviesListBinding? = null
    private val binding: FragmentMoviesListBinding get() = _binding!!
    private lateinit var movieType: MovieType
    private lateinit var genres: MutableList<Genre>
    private lateinit var adapter: MoviesListRvAdapter
    private val moviesViewModel: MoviesViewModel by activityViewModels()

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
        binding.apply {
            when(movieType) {
                MovieType.LATEST -> {
                    titleTv.text = getString(R.string.latest_movies)
                    moviesViewModel.latestMovies.observe(viewLifecycleOwner, { handleResponse(it) })
                }
                MovieType.POPULAR -> {
                    titleTv.text = getString(R.string.popular_movies)
                    moviesViewModel.popularMovies.observe(viewLifecycleOwner, { handleResponse(it) })
                }
                MovieType.UPCOMING -> {
                    titleTv.text = getString(R.string.upcoming_movies)
                    moviesViewModel.upcomingMovies.observe(viewLifecycleOwner, { handleResponse(it) })
                }
            }
            moviesViewModel.moviesGenres.observe(viewLifecycleOwner, { resource ->
                if(resource is Resource.Error) showSnackBar(resource.message)
                resource.data.let { genreList ->
                    genres.clear()
                    genreList?.forEach { genres.add(it) }
                }
            })
            backBtn.setOnClickListener { findNavController().popBackStack() }
        }
    }

    private fun handleResponse(resource: Resource<List<Movie>>) {
        binding.apply {
            resource.data?.let { it -> setUpRecyclerView(it) }
            Utils.setUpSearchView(searchSv, adapter)
            when(resource) {
                is Resource.Loading -> {
                    moviesListRetryLl.gone()
                    moviesListPb.isVisible = moviesListRv.isEmpty()
                }
                is Resource.Success -> {
                    moviesListRetryLl.gone()
                    moviesListPb.gone()
                }
                is Resource.Error -> {
                    if(resource.data?.isNotEmpty() == true) moviesListRetryLl.gone()
                    if(moviesListRv.childCount == 0) {
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
        val movieGenres = mutableListOf<String>()
        val itemGenre = item.genres?.get(0)
        genres.forEach { genre ->
            if(itemGenre!!.contains(genre.id.toString())) {
                movieGenres.add(genre.name.toString())
            }
        }
        val bundle = bundleOf(
            Constants.MOVIE_BUNDLE_KEY to item,
            Constants.GENRES_BUNDLE_KEY to movieGenres
        )
        findNavController().navigate(R.id.movieDetailsFragment, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}