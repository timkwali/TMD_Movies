package com.timkwali.tmdmovies.moviedetails.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.timkwali.tmdmovies.common.utils.Constants
import com.timkwali.tmdmovies.common.utils.Utils.addChips
import com.timkwali.tmdmovies.common.utils.Utils.loadImage
import com.timkwali.tmdmovies.databinding.FragmentMovieDetailsBinding
import com.timkwali.tmdmovies.moviescategories.domain.model.Movie

class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var movie: Movie
    private lateinit var genres: MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBundleData()
        setUpViews(movie)
    }

    private fun getBundleData() {
        movie = arguments?.get(Constants.MOVIE_BUNDLE_KEY) as Movie
        genres = arguments?.get(Constants.GENRES_BUNDLE_KEY) as MutableList<String>
    }

    private fun setUpViews(movie: Movie) {
        binding.apply {
//            val imageUrl = Constants.IMAGE_URL + movie.image
            val imageUrl = "http://image.tmdb.org/t/p/w500/9Rj8l6gElLpRL7Kj17iZhrT5Zuw.jpg"
            moviePoster.loadImage(imageUrl)
            movieNameTv.text = movie.title
            ratingTv.text = "${movie.rating}/10.0 TMDB"
            genresCg.addChips(genres, false)
            releaseDateTv.text = movie.releaseDate
            languageTv.text = movie.language
            votesTv.text = movie.voteCount.toString()
            descriptionTv.text = movie.description
            backBtn.setOnClickListener { findNavController().popBackStack() }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}