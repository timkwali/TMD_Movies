package com.timkwali.tmdmovies.common.data.model.popularmovies

import com.google.gson.annotations.SerializedName

data class PopularMoviesResponse(
    val page: Int?,
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)

