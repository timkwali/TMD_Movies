package com.timkwali.tmdmovies.common.data.cache.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.timkwali.tmdmovies.common.data.model.popularmovies.PopularMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("SELECT * FROM popular_movies_table")
    fun getPopularMovies(): Flow<List<PopularMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePopularMovies(popularMovies: List<PopularMovie>)

    @Query("DELETE FROM popular_movies_table")
    suspend fun deleteAllPopularMovies()
}