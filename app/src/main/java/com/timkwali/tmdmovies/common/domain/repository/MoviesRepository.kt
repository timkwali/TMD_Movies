package com.timkwali.tmdmovies.common.domain.repository

import com.timkwali.tmdmovies.common.data.model.popularmovies.PopularMoviesResponse

interface MoviesRepository {

    suspend fun getPopularMovies(): PopularMoviesResponse
}