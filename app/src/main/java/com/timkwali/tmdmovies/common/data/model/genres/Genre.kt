package com.timkwali.tmdmovies.common.data.model.genres


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.timkwali.tmdmovies.common.utils.Constants

@Entity(tableName = Constants.MOVIES_GENRES_TABLE)
data class Genre(
    @PrimaryKey
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)