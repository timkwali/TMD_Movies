package com.timkwali.tmdmovies.common.domain.usecases

import com.timkwali.tmdmovies.common.data.model.genres.Genre
import com.timkwali.tmdmovies.common.data.networkBoundResource
import com.timkwali.tmdmovies.common.domain.repository.MoviesRepository
import com.timkwali.tmdmovies.common.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGenres @Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke(): Flow<Resource<List<Genre>>> = networkBoundResource(
        query = {
            repository.getDbGenres()
        },
        fetch = {
            repository.getGenres().genres
        },
        saveFetchResult = { genreList ->
            if (genreList != null) {
                repository.saveMoviesGenres(genreList)
            }
        }
    )
}