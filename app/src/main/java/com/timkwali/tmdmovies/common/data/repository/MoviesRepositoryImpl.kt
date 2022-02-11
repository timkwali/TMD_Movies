package com.timkwali.tmdmovies.common.data.repository

import com.timkwali.tmdmovies.common.data.api.MoviesApi
import com.timkwali.tmdmovies.common.data.model.popularmovies.PopularMoviesResponse
import com.timkwali.tmdmovies.common.domain.repository.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi
): MoviesRepository {
    override suspend fun getPopularMovies(): PopularMoviesResponse {
        return moviesApi.getPopularMovies()
    }
}