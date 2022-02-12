package com.timkwali.tmdmovies.common.data.model.genres


import com.google.gson.annotations.SerializedName

data class Genres(
    @SerializedName("genres")
    val genres: List<Genre>?
)