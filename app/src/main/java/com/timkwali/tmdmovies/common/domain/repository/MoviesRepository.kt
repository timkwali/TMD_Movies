package com.timkwali.tmdmovies.common.domain.repository

import com.timkwali.tmdmovies.common.data.model.popularmovies.PopularMovie
import com.timkwali.tmdmovies.common.data.model.popularmovies.PopularMoviesResponse
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    /** POPULAR MOVIES */
    suspend fun getPopularMovies(): PopularMoviesResponse
    fun getDbPopularMovies(): Flow<List<PopularMovie>>
    suspend fun savePopularMovies(popularMovies: List<PopularMovie>)
    suspend fun deleteAllPopularMovies()

    /** LATEST MOVIES */

    /** UPCOMING MOVIES */
}