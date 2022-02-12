package com.timkwali.tmdmovies.common.data.cache.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.timkwali.tmdmovies.common.data.model.genres.Genre
import com.timkwali.tmdmovies.common.data.model.latestmovies.LatestMoviesResponse
import com.timkwali.tmdmovies.common.data.model.popularmovies.PopularMovie
import com.timkwali.tmdmovies.common.data.model.upcomingmovies.UpcomingMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    /** LATEST MOVIES */
    @Query("SELECT * FROM latest_movies_table")
    fun getLatestMovies(): Flow<List<LatestMoviesResponse>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLatestMovies(LatestMovies: List<LatestMoviesResponse>)

    @Query("DELETE FROM latest_movies_table")
    suspend fun deleteAllLatestMovies()

    /** POPULAR MOVIES */
    @Query("SELECT * FROM popular_movies_table")
    fun getPopularMovies(): Flow<List<PopularMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePopularMovies(popularMovies: List<PopularMovie>)

    @Query("DELETE FROM popular_movies_table")
    suspend fun deleteAllPopularMovies()

    /** UPCOMING MOVIES */
    @Query("SELECT * FROM upcoming_movies_table")
    fun getUpcomingMovies(): Flow<List<UpcomingMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUpcomingMovies(upcomingMovies: List<UpcomingMovie>)

    @Query("DELETE FROM upcoming_movies_table")
    suspend fun deleteAllUpcomingMovies()

    /** MOVIES GENRE */
    @Query("SELECT * FROM movies_genre_table")
    fun getMoviesGenres(): Flow<List<Genre>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMoviesGenres(moviesGenres: List<Genre>)

    @Query("DELETE FROM movies_genre_table")
    suspend fun deleteAllMoviesGenres()
}