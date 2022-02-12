package com.timkwali.tmdmovies.moviescategories.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timkwali.tmdmovies.common.utils.Constants
import com.timkwali.tmdmovies.common.utils.OnItemClick
import com.timkwali.tmdmovies.common.utils.Utils.loadImage
import com.timkwali.tmdmovies.databinding.MovieCategoryItemBinding
import com.timkwali.tmdmovies.moviescategories.domain.model.Movie

class MovieCategoryRvAdapter(private val moviesList: List<Movie>, private val listener: OnItemClick<Movie>):
    RecyclerView.Adapter<MovieCategoryRvAdapter.MovieCategoryViewHolder>() {

    inner class MovieCategoryViewHolder(private val binding: MovieCategoryItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, action: OnItemClick<Movie>) {
            binding.apply {
                val imageUrl = Constants.IMAGE_URL + movie.image
                categoryMovieImageIv.loadImage(imageUrl)
                categoryMovieTitleTv.text = movie.title
                categoryRatingTv.text = "${movie.rating}/10.0 TMDB"
            }
            itemView.setOnClickListener {
                action.onItemClick(movie, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCategoryViewHolder {
        val binding = MovieCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieCategoryViewHolder, position: Int) {
        val currentMovie = moviesList[position]
        holder.bind(currentMovie, listener)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }
}