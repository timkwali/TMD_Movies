package com.timkwali.tmdmovies.common.data.api

import com.timkwali.tmdmovies.common.data.model.genres.Genres
import com.timkwali.tmdmovies.common.data.model.latestmovies.LatestMoviesResponse
import com.timkwali.tmdmovies.common.data.model.popularmovies.PopularMoviesResponse
import com.timkwali.tmdmovies.common.data.model.upcomingmovies.UpcomingMoviesResponse
import com.timkwali.tmdmovies.common.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/latest")
    suspend fun getLatestMovies(
        @Query("page") page: Int = Constants.PAGE_NUMBER,
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = Constants.LANGUAGE
    ): LatestMoviesResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = Constants.PAGE_NUMBER,
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = Constants.LANGUAGE
    ): PopularMoviesResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int = Constants.PAGE_NUMBER,
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = Constants.LANGUAGE
    ): UpcomingMoviesResponse

    @GET("genre/movie/list")
    suspend fun getMoviesGenre(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = Constants.LANGUAGE
    ): Genres
}