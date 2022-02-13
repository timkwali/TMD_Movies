package com.timkwali.tmdmovies.common.utils

import com.timkwali.tmdmovies.BuildConfig


object Constants {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val LANGUAGE = "en-US"
//    const val API_KEY = BuildConfig.API_KEY
    const val API_KEY = "6426fc91f9afa2a87ffad670424178ee"
    const val IMAGE_URL = "http://image.tmdb.org/t/p/w500"
    const val PAGE_NUMBER = 1
    const val PER_PAGE = 20
    const val DATABASE_NAME = "movies_db"
    const val LATEST_MOVIES_TABLE = "latest_movies_table"
    const val POPULAR_MOVIES_TABLE = "popular_movies_table"
    const val UPCOMING_MOVIES_TABLE = "upcoming_movies_table"
    const val MOVIES_GENRES_TABLE = "movies_genre_table"

    const val MOVIE_BUNDLE_KEY = "movie"
    const val GENRES_BUNDLE_KEY = "genres"
    const val MOVIE_TYPE_BUNDLE_KEY = "movieType"

    const val SPLASH_DURATION: Long = 3000
}