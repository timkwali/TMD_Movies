package com.timkwali.tmdmovies.moviescategories.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.timkwali.tmdmovies.R
import com.timkwali.tmdmovies.moviescategories.domain.model.Movie
import com.timkwali.tmdmovies.common.utils.OnItemClick
import com.timkwali.tmdmovies.databinding.MovieCategoryItemBinding

class MovieCategoryRvAdapter(val moviesList: List<Movie>, val listener: OnItemClick<Movie>):
    RecyclerView.Adapter<MovieCategoryRvAdapter.MovieCategoryViewHolder>() {

    inner class MovieCategoryViewHolder(private val binding: MovieCategoryItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, action: OnItemClick<Movie>) {
            binding.apply {
                categoryMovieTitleTv.text = movie.title
                categoryRatingTv.text = "${movie.rating}/10.0 TMDB"
                Glide.with(categoryMovieImageIv)
                    .load(movie.image)
                    .placeholder(R.drawable.ic_round_camera)
                    .error(R.drawable.tmd_logo)
                    .into(categoryMovieImageIv)
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