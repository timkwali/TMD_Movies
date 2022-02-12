package com.timkwali.tmdmovies.moviescategories.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int?,
    val title: String?,
    val rating: Double?,
    val image: String?,
    val genres: List<String>?,
    val releaseDate: String?,
    val voteCount: Int?,
    val language: String?,
    val description: String?,
) : Parcelable
