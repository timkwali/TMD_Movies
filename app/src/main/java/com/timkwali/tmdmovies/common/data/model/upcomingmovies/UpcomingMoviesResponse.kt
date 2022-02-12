package com.timkwali.tmdmovies.common.data.model.upcomingmovies

import com.google.gson.annotations.SerializedName

data class UpcomingMoviesResponse(
    val dates: Dates,
    val page: Int,
    val results: List<UpcomingMovie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
