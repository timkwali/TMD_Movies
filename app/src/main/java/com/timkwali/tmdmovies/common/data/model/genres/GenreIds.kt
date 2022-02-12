package com.timkwali.tmdmovies.common.data.model.genres

import androidx.room.TypeConverter

data class GenreIds(
    val ids: List<Int>
)
//{
//    @TypeConverter
//    fun toInt(id: Int): Int {
//        return id
//    }
//}
