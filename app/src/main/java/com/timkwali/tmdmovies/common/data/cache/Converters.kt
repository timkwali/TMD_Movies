package com.timkwali.tmdmovies.common.data.cache

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import com.timkwali.tmdmovies.common.data.model.latestmovies.ProductionCompanies
import com.timkwali.tmdmovies.common.data.model.latestmovies.ProductionCountries
import com.timkwali.tmdmovies.common.data.model.latestmovies.SpokenLanguage
import java.lang.reflect.Type


class Converters {

    /** LIST OF INT */
    @TypeConverter
    fun toList(value: String): List<String> {
        return listOf(value)
    }
    @TypeConverter
    fun fromList(list: List<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    /** LIST OF ProductionCompanies */
    @TypeConverter
    fun toProductionCompanies(value: String): List<ProductionCompanies> {
        val listType: Type = object : TypeToken<List<ProductionCompanies>>() {}.type
        return Gson().fromJson(value, listType)
    }
    @TypeConverter
    fun fromProductionCompanies(list: List<ProductionCompanies>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    /** LIST OF ProductionCountries */
    @TypeConverter
    fun toProductionCountries(value: String): List<ProductionCountries> {
        val listType: Type = object : TypeToken<List<ProductionCountries>>() {}.type
        return Gson().fromJson(value, listType)
    }
    @TypeConverter
    fun fromProductionCountries(list: List<ProductionCountries>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    /** LIST OF SpokenLanguage */
    @TypeConverter
    fun toSpokenLanguage(value: String?): List<SpokenLanguage> {
        val listType: Type = object : TypeToken<List<SpokenLanguage>>() {}.type
        return Gson().fromJson(value, listType)
    }
    @TypeConverter
    fun fromSpokenLanguage(list: List<SpokenLanguage>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}