package com.timkwali.tmdmovies.common.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.timkwali.tmdmovies.common.data.cache.dao.MoviesDao
import com.timkwali.tmdmovies.common.data.cache.dao.MoviesRemoteKeysDao
import com.timkwali.tmdmovies.common.data.model.popularmovies.PopularMovie
import com.timkwali.tmdmovies.common.utils.Constants

@Database(
    entities = [PopularMovie::class],
    version = 1
)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao

    abstract fun moviesRemoteKeysDao(): MoviesRemoteKeysDao

    companion object {
        const val DATABASE_NAME = Constants.DATABASE_NAME
    }
}