package com.timkwali.tmdmovies.common.data.model.remotekeys

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.timkwali.tmdmovies.common.utils.Constants

@Entity(tableName = Constants.MOVIES_REMOTE_KEYS_TABLE)
data class MoviesRemoteKeys(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val prevPage: Int?,
    val nextPage: Int?
)
