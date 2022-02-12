package com.timkwali.tmdmovies.common.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.timkwali.tmdmovies.common.data.cache.dao.MoviesDao
import com.timkwali.tmdmovies.common.data.model.genres.Genre
import com.timkwali.tmdmovies.common.data.model.latestmovies.LatestMoviesResponse
import com.timkwali.tmdmovies.common.data.model.popularmovies.PopularMovie
import com.timkwali.tmdmovies.common.data.model.upcomingmovies.UpcomingMovie
import com.timkwali.tmdmovies.common.utils.Constants

@Database(
    entities = [
        PopularMovie::class,
        UpcomingMovie::class,
        LatestMoviesResponse::class,
        Genre::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao

    companion object {
        const val DATABASE_NAME = Constants.DATABASE_NAME
    }
}