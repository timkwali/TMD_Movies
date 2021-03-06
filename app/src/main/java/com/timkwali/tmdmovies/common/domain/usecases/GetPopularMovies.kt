package com.timkwali.tmdmovies.common.domain.usecases

import com.timkwali.tmdmovies.common.data.networkBoundResource
import com.timkwali.tmdmovies.common.domain.repository.MoviesRepository
import com.timkwali.tmdmovies.common.utils.Resource
import com.timkwali.tmdmovies.common.data.mappers.PopularMoviesMapper
import com.timkwali.tmdmovies.common.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPopularMovies @Inject constructor(
    private val repository: MoviesRepository
) {

    operator fun invoke(): Flow<Resource<List<Movie>>> = networkBoundResource(
        query = {
            repository.getDbPopularMovies().map {
                it.map { popularMovie ->
                    if(popularMovie.adult == true) {
                        popularMovie.posterPath = ""
                    }
                    PopularMoviesMapper().mapToDomain(popularMovie)
                }
            }
        },
        fetch = {
            repository.getPopularMovies()
        },
        saveFetchResult = {
            val popularMovies = it.results
            repository.savePopularMovies(popularMovies)
        }
    )
}