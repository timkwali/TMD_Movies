package com.timkwali.tmdmovies.common.domain.usecases

import com.timkwali.tmdmovies.common.data.networkBoundResource
import com.timkwali.tmdmovies.common.domain.repository.MoviesRepository
import com.timkwali.tmdmovies.common.utils.Resource
import com.timkwali.tmdmovies.moviescategories.data.mappers.LatestMoviesMapper
import com.timkwali.tmdmovies.moviescategories.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetLatestMovies @Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke(): Flow<Resource<List<Movie>>> = networkBoundResource(
        query = {
            repository.getDbLatestMovies().map {
                it.map { latestMovie ->
                    LatestMoviesMapper().mapToDomain(latestMovie)
                }
            }
        },
        fetch = {
            repository.getLatestMovies()
        },
        saveFetchResult = {
            val latestMovies = listOf(it)
            repository.saveLatestMovies(latestMovies)
        }
    )
}