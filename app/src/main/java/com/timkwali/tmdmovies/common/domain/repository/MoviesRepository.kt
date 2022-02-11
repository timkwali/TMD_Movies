package com.timkwali.tmdmovies.common.domain.repository

import com.timkwali.tmdmovies.common.data.model.popularmovies.PopularMoviesResponse
import com.timkwali.tmdmovies.common.data.model.popularmovies.Result
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getPopularMovies(): PopularMoviesResponse
}