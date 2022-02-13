package com.timkwali.tmdmovies.moviescategories.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timkwali.tmdmovies.common.data.model.genres.Genre
import com.timkwali.tmdmovies.common.domain.model.Movie
import com.timkwali.tmdmovies.common.domain.usecases.GetGenres
import com.timkwali.tmdmovies.common.domain.usecases.GetLatestMovies
import com.timkwali.tmdmovies.common.domain.usecases.GetPopularMovies
import com.timkwali.tmdmovies.common.domain.usecases.GetUpcomingMovies
import com.timkwali.tmdmovies.common.utils.MovieType
import com.timkwali.tmdmovies.common.utils.Resource
import com.timkwali.tmdmovies.moviescategories.domain.usecase.GetMoviesCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesCategoriesViewModel @Inject constructor(
    private val getMoviesCategory: GetMoviesCategory,
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
        getMoviesCategory(MovieType.LATEST).collect { _latestMovies.value = it }
    }

    fun getPopularMovies() = viewModelScope.launch {
        getMoviesCategory(MovieType.POPULAR).collect{ _popularMovies.value = it }
    }

    fun getUpcomingMovies() = viewModelScope.launch {
        getMoviesCategory(MovieType.UPCOMING).collect { _upcomingMovies.value = it }
    }

    fun getMoviesGenres() = viewModelScope.launch {
        getGenres().collect { _moviesGenres.value = it }
    }
}