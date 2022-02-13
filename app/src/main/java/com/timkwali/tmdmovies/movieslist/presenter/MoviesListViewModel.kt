package com.timkwali.tmdmovies.movieslist.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timkwali.tmdmovies.common.data.model.genres.Genre
import com.timkwali.tmdmovies.common.domain.model.Movie
import com.timkwali.tmdmovies.common.domain.usecases.GetGenres
import com.timkwali.tmdmovies.common.utils.MovieType
import com.timkwali.tmdmovies.common.utils.Resource
import com.timkwali.tmdmovies.movieslist.domain.usecase.GetMoviesList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val getMoviesList: GetMoviesList,
    private val getGenres: GetGenres
): ViewModel() {

    private var _moviesList = MutableLiveData<Resource<List<Movie>>>()
    val moviesList: LiveData<Resource<List<Movie>>> get() = _moviesList

    private var _moviesGenres = MutableLiveData<Resource<List<Genre>>>()
    val moviesGenres: LiveData<Resource<List<Genre>>> get() = _moviesGenres

    init {
        getMoviesGenres()
    }

    fun getMovies(movieType: MovieType) = viewModelScope.launch {
        getMoviesList(movieType).collect { _moviesList.value = it }
    }

    fun getMoviesGenres() = viewModelScope.launch {
        getGenres.invoke().collect { _moviesGenres.value = it }
    }

}