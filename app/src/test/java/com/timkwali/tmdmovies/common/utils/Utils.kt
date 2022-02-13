package com.timkwali.tmdmovies.common.utils

import com.timkwali.tmdmovies.common.data.model.genres.Genre
import com.timkwali.tmdmovies.common.domain.model.Movie
import kotlinx.coroutines.flow.Flow

object Utils {

    fun getCategoriesData(dataStates: DataStates): Flow<Resource<List<Movie>>> {
        return when(dataStates) {
            DataStates.LOADING -> Constants.movieLoadingData
            DataStates.SUCCESS -> Constants.movieSuccessData
            DataStates.ERROR -> Constants.movieErrorData
        }
    }

    fun getGenresData(dataStates: DataStates): Flow<Resource<List<Genre>>> {
        return when(dataStates) {
            DataStates.LOADING -> Constants.genreLoadingData
            DataStates.SUCCESS -> Constants.genreSuccessData
            DataStates.ERROR -> Constants.genreErrorData
        }
    }
}