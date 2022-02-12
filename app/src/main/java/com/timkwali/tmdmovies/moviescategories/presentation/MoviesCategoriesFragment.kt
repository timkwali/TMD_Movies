package com.timkwali.tmdmovies.moviescategories.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
import com.timkwali.tmdmovies.databinding.FragmentMoviesCategoriesBinding
import com.timkwali.tmdmovies.moviescategories.domain.model.Movie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesCategoriesFragment : Fragment(), OnItemClick<Movie> {

    private var _binding: FragmentMoviesCategoriesBinding? = null
    private val binding get() = _binding!!
    private val moviesCategoriesViewModel: MoviesViewModel by viewModels()
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
            setClickListeners()
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
                progressBar.isVisible = recyclerView.isEmpty()
            }
            is Resource.Success -> {
                retry.gone()
                progressBar.gone()
            }
            is Resource.Error -> {
                if(resource.data?.isNotEmpty() == true) retry.gone()
                if(recyclerView.childCount == 0) {
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