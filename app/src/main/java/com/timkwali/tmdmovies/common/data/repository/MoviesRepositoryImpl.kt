package com.timkwali.tmdmovies.common.data.repository

import com.timkwali.tmdmovies.common.data.api.MoviesApi
import com.timkwali.tmdmovies.common.data.cache.MoviesDatabase
import com.timkwali.tmdmovies.common.data.model.popularmovies.PopularMovie
import com.timkwali.tmdmovies.common.data.model.popularmovies.PopularMoviesResponse
import com.timkwali.tmdmovies.common.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi,
    moviesDatabase: MoviesDatabase
): MoviesRepository {

    private val moviesDao = moviesDatabase.moviesDao()

    /** POPULAR MOVIES */
    override suspend fun getPopularMovies(): PopularMoviesResponse {
        return moviesApi.getPopularMovies()
    }
    override fun getDbPopularMovies(): Flow<List<PopularMovie>> {
        return moviesDao.getPopularMovies()
    }
    override suspend fun savePopularMovies(popularMovies: List<PopularMovie>) {
        moviesDao.savePopularMovies(popularMovies)
    }
    override suspend fun deleteAllPopularMovies() {
        moviesDao.deleteAllPopularMovies()
    }
}