package com.timkwali.tmdmovies.common.presentation

import androidx.lifecycle.*
import com.timkwali.tmdmovies.common.data.model.genres.Genre
import com.timkwali.tmdmovies.common.domain.usecases.GetGenres
import com.timkwali.tmdmovies.common.utils.Resource
import com.timkwali.tmdmovies.moviescategories.domain.model.Movie
import com.timkwali.tmdmovies.common.domain.usecases.GetLatestMovies
import com.timkwali.tmdmovies.common.domain.usecases.GetPopularMovies
import com.timkwali.tmdmovies.common.domain.usecases.GetUpcomingMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies,
    private val getUpcomingMovies: GetUpcomingMovies,
    private val getLatestMovies: GetLatestMovies,
    private val getGenres: GetGenres
): ViewModel() {

    private var _latestMovies =  MutableLiveData<Resource<List<Movie>>>()
    val latestMovies: LiveData<Resource<List<Movie>>> get() = _latestMovies

    private var _popularMovies = MutableLiveData<Resource<List<Movie>>>()
    val popularMovies: LiveData<Resource<List<Movie>>> get() = _popularMovies

    private var _upcomingMovies = MutableLiveData<Resource<List<Movie>>>()
    val upcomingMovies: LiveData<Resource<List<Movie>>> get() = _upcomingMovies

    private var _moviesGenres = MutableLiveData<Resource<List<Genre>>>()
    val moviesGenres: LiveData<Resource<List<Genre>>> get() = _moviesGenres

    init {
        getLatestMovies()
        getPopularMovies()
        getUpcomingMovies()
        getMoviesGenres()
    }

    fun getLatestMovies()  = viewModelScope.launch {
        getLatestMovies.invoke().collect { _latestMovies.value = it }
    }

    fun getPopularMovies() = viewModelScope.launch {
        getPopularMovies.invoke().collect{ _popularMovies.value = it }
    }

    fun getUpcomingMovies() = viewModelScope.launch {
        getUpcomingMovies.invoke().collect { _upcomingMovies.value = it }
    }

    private fun getMoviesGenres() = viewModelScope.launch {
        getGenres.invoke().collect { _moviesGenres.value = it }
    }
}