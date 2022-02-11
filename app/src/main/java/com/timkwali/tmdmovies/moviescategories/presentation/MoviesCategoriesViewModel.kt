package com.timkwali.tmdmovies.moviescategories.presentation

import android.util.Log
import androidx.lifecycle.*
import com.timkwali.tmdmovies.moviescategories.domain.model.Movie
import com.timkwali.tmdmovies.moviescategories.domain.usecases.GetPopularMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesCategoriesViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies
): ViewModel() {

    lateinit var popularMovies: LiveData<List<Movie>>
    lateinit var latestMovies: LiveData<List<Movie>>
    lateinit var upcomingMovies: LiveData<List<Movie>>

    init {
        viewModelScope.launch {
            getPopularMovies()
        }
    }

    private fun getPopularMovies() {
        popularMovies = getPopularMovies.invoke().asLiveData()
    }
}