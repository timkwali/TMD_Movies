package com.timkwali.tmdmovies.common.data.api

import com.timkwali.tmdmovies.common.data.model.popularmovies.PopularMoviesResponse
import com.timkwali.tmdmovies.common.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = Constants.PAGE_NUMBER,
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = Constants.LANGUAGE
    ): PopularMoviesResponse
}