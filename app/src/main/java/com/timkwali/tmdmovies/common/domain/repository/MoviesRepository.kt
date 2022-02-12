package com.timkwali.tmdmovies.common.domain.repository

import com.timkwali.tmdmovies.common.data.model.genres.Genre
import com.timkwali.tmdmovies.common.data.model.genres.Genres
import com.timkwali.tmdmovies.common.data.model.latestmovies.LatestMoviesResponse
import com.timkwali.tmdmovies.common.data.model.popularmovies.PopularMovie
import com.timkwali.tmdmovies.common.data.model.popularmovies.PopularMoviesResponse
import com.timkwali.tmdmovies.common.data.model.upcomingmovies.UpcomingMovie
import com.timkwali.tmdmovies.common.data.model.upcomingmovies.UpcomingMoviesResponse
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    /** LATEST MOVIES */
    suspend fun getLatestMovies(): LatestMoviesResponse
    fun getDbLatestMovies(): Flow<List<LatestMoviesResponse>>
    suspend fun saveLatestMovies(latestMovies: List<LatestMoviesResponse>)
    suspend fun deleteAllLatestMovies()

    /** POPULAR MOVIES */
    suspend fun getPopularMovies(): PopularMoviesResponse
    fun getDbPopularMovies(): Flow<List<PopularMovie>>
    suspend fun savePopularMovies(popularMovies: List<PopularMovie>)
    suspend fun deleteAllPopularMovies()

    /** UPCOMING MOVIES */
    suspend fun getUpcomingMovies(): UpcomingMoviesResponse
    fun getDbUpcomingMovies(): Flow<List<UpcomingMovie>>
    suspend fun saveUpcomingMovies(upcomingMovies: List<UpcomingMovie>)
    suspend fun deleteAllUpcomingMovies()

    /** GENRES */
    suspend fun getGenres(): Genres
    fun getDbGenres(): Flow<List<Genre>>
    suspend fun saveMoviesGenres(moviesGenres: List<Genre>)
    suspend fun deleteAllMoviesGenres()
}