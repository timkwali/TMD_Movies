package com.timkwali.tmdmovies.common.data.model.latestmovies

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.RawValue

data class ProductionCompanies(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("logo_path")
    val logoPath: String?,
    @SerializedName("origin_country")
    val originalCountry: String?
)
