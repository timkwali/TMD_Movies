package com.timkwali.tmdmovies.movieslist.presenter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.timkwali.tmdmovies.common.data.model.genres.Genre
import com.timkwali.tmdmovies.common.utils.Constants
import com.timkwali.tmdmovies.common.utils.OnItemClick
import com.timkwali.tmdmovies.common.utils.Utils.addChips
import com.timkwali.tmdmovies.common.utils.Utils.genreIdsToGenreNames
import com.timkwali.tmdmovies.common.utils.Utils.loadImage
import com.timkwali.tmdmovies.databinding.MoviesListItemBinding
import com.timkwali.tmdmovies.moviescategories.domain.model.Movie
import java.util.*

class MoviesListRvAdapter(
    private val moviesList: List<Movie>,
    private val genresList: List<Genre>,
    private val listener: OnItemClick<Movie>
): RecyclerView.Adapter<MoviesListRvAdapter.MoviesListViewHolder>(), Filterable {

    /** SET UP SEARCH */
    var moviesFilterList = mutableListOf<Movie>()
    init {
        moviesFilterList = moviesList.toMutableList()
    }

    inner class MoviesListViewHolder(private val binding: MoviesListItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, action: OnItemClick<Movie>) {
            binding.apply {
                movieNameTv.text = movie.title
                ratingTv.text = "${movie.rating}/10.0 TMDB"
                releaseDateTv.text = movie.releaseDate

                val imageUrl = Constants.IMAGE_URL + movie.image
                categoryMovieImageIv.loadImage(imageUrl)

                val idList = movie.genres?.get(0)
                val genreNames = mutableListOf<String>()
                genresList.forEach { genre ->
                    if(idList!!.contains(genre.id.toString())) {
                        genreNames.add(genre.name.toString())
                    }
                }
                genresCg.addChips(genreNames, true)
            }
            itemView.setOnClickListener {
                action.onItemClick(movie, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        val binding = MoviesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) {
        val currentMovie = moviesFilterList[position]
        holder.bind(currentMovie, listener)
    }

    override fun getItemCount(): Int {
        return moviesFilterList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                moviesFilterList = if(charSearch.isEmpty()) {
                    moviesList.toMutableList()
                } else {
                    val resultList = mutableListOf<Movie>()
                    for(movie in moviesList) {
                        if(movie.title?.toLowerCase(Locale.ROOT)?.contains(charSearch.toLowerCase(Locale.ROOT)) == true) {
                            resultList.add(movie)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = moviesFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                moviesFilterList = results?.values as MutableList<Movie>
                notifyDataSetChanged()
            }
        }
    }
}