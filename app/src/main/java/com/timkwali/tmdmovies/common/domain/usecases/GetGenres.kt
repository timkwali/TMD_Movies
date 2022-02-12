package com.timkwali.tmdmovies.common.domain.usecases

import com.timkwali.tmdmovies.common.data.model.genres.Genre
import com.timkwali.tmdmovies.common.data.networkBoundResource
import com.timkwali.tmdmovies.common.domain.repository.MoviesRepository
import com.timkwali.tmdmovies.common.utils.Resource
import com.timkwali.tmdmovies.moviescategories.data.mappers.LatestMoviesMapper
import com.timkwali.tmdmovies.moviescategories.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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