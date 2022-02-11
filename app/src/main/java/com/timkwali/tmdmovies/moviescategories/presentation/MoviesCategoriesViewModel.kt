package com.timkwali.tmdmovies.moviescategories.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.timkwali.tmdmovies.common.utils.Resource
import com.timkwali.tmdmovies.moviescategories.domain.model.Movie
import com.timkwali.tmdmovies.moviescategories.domain.usecases.GetPopularMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesCategoriesViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies
): ViewModel() {

    lateinit var popularMovies: LiveData<Resource<List<Movie>>>
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