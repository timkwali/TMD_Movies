package com.timkwali.tmdmovies.common.data.cache.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface MoviesDao {

    @Query("SELECT * FROM popularmovie")
    fun getPopularMovies()
}