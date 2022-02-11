package com.timkwali.tmdmovies.moviescategories.domain.model

data class Movie(
    val id: Int?,
    val title: String?,
    val rating: Double?,
    val image: String?,
//    val genres: List<Int>?,
    val releaseDate: String?,
    val language: String?,
    val description: String?,
)
