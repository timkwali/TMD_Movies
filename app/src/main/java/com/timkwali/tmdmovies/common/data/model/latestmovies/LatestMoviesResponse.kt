package com.timkwali.tmdmovies.common.data.model.latestmovies

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.timkwali.tmdmovies.common.utils.Constants

@Entity(tableName = Constants.LATEST_MOVIES_TABLE)
data class LatestMoviesResponse(
    @PrimaryKey
    @SerializedName("id")
    val id: Int?,
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: String?,
    @SerializedName("budget")
    val budget: Int?,
//    @SerializedName("genres")
//    val genres: List<Int>?,
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("imdb_id")
    val imdbId: String?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("popularity")
    val popularity: Int?,
    @SerializedName("poster_path")
    var posterPath: String?,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompanies>,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountries>,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("revenue")
    val revenue: Int?,
    @SerializedName("runtime")
    val runtime: Int?,
//    @SerializedName("spoken_languages")
//    val spokenLanguage: List<SpokenLanguage>?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("tagline")
    val tagline: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("video")
    val video: Boolean?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?

)
