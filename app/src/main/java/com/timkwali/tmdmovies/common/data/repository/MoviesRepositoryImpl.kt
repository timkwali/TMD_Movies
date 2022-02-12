package com.timkwali.tmdmovies.common.data.repository

import androidx.room.withTransaction
import com.timkwali.tmdmovies.common.data.api.MoviesApi
import com.timkwali.tmdmovies.common.data.cache.MoviesDatabase
import com.timkwali.tmdmovies.common.data.model.genres.Genre
import com.timkwali.tmdmovies.common.data.model.genres.Genres
import com.timkwali.tmdmovies.common.data.model.latestmovies.LatestMoviesResponse
import com.timkwali.tmdmovies.common.data.model.popularmovies.PopularMovie
import com.timkwali.tmdmovies.common.data.model.popularmovies.PopularMoviesResponse
import com.timkwali.tmdmovies.common.data.model.upcomingmovies.UpcomingMovie
import com.timkwali.tmdmovies.common.data.model.upcomingmovies.UpcomingMoviesResponse
import com.timkwali.tmdmovies.common.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi,
    private val moviesDatabase: MoviesDatabase
): MoviesRepository {

    private val moviesDao = moviesDatabase.moviesDao()

    /** LATEST MOVIES */
    override suspend fun getLatestMovies(): LatestMoviesResponse {
        return moviesApi.getLatestMovies()
    }
    override fun getDbLatestMovies(): Flow<List<LatestMoviesResponse>> {
        return moviesDao.getLatestMovies()
    }
    override suspend fun saveLatestMovies(latestMovies: List<LatestMoviesResponse>) {
        moviesDatabase.withTransaction {
            deleteAllLatestMovies()
            moviesDao.saveLatestMovies(latestMovies)
        }
    }
    override suspend fun deleteAllLatestMovies() {
        moviesDao.deleteAllLatestMovies()
    }

    /** POPULAR MOVIES */
    override suspend fun getPopularMovies(): PopularMoviesResponse {
        return moviesApi.getPopularMovies()
    }
    override fun getDbPopularMovies(): Flow<List<PopularMovie>> {
        return moviesDao.getPopularMovies()
    }
    override suspend fun savePopularMovies(popularMovies: List<PopularMovie>) {
        moviesDatabase.withTransaction {
            deleteAllPopularMovies()
            moviesDao.savePopularMovies(popularMovies)
        }
    }
    override suspend fun deleteAllPopularMovies() {
        moviesDao.deleteAllPopularMovies()
    }

    /** UPCOMING MOVIES */
    override suspend fun getUpcomingMovies(): UpcomingMoviesResponse {
        return moviesApi.getUpcomingMovies()
    }
    override fun getDbUpcomingMovies(): Flow<List<UpcomingMovie>> {
        return moviesDao.getUpcomingMovies()
    }
    override suspend fun saveUpcomingMovies(upcomingMovies: List<UpcomingMovie>) {
        moviesDatabase.withTransaction {
            deleteAllUpcomingMovies()
            moviesDao.saveUpcomingMovies(upcomingMovies)
        }
    }
    override suspend fun deleteAllUpcomingMovies() {
        moviesDao.deleteAllUpcomingMovies()
    }

    /** MOVIES GENRES */
    override suspend fun getGenres(): Genres {
        return moviesApi.getMoviesGenre()
    }
    override fun getDbGenres(): Flow<List<Genre>> {
        return moviesDao.getMoviesGenres()
    }
    override suspend fun saveMoviesGenres(moviesGenres: List<Genre>) {
        moviesDatabase.withTransaction {
            deleteAllMoviesGenres()
            moviesDao.saveMoviesGenres(moviesGenres)
        }
    }
    override suspend fun deleteAllMoviesGenres() {
        moviesDao.deleteAllMoviesGenres()
    }
}