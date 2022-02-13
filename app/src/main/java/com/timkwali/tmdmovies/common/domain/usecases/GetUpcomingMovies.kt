package com.timkwali.tmdmovies.common.domain.usecases

import com.timkwali.tmdmovies.common.data.networkBoundResource
import com.timkwali.tmdmovies.common.domain.repository.MoviesRepository
import com.timkwali.tmdmovies.common.utils.Resource
import com.timkwali.tmdmovies.common.data.mappers.UpcomingMoviesMapper
import com.timkwali.tmdmovies.common.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUpcomingMovies @Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke(): Flow<Resource<List<Movie>>> = networkBoundResource(
        query = {
            repository.getDbUpcomingMovies().map {
                it.map { upcomingMovie ->
                    if(upcomingMovie.adult == true) {
                        upcomingMovie.posterPath = ""
                    }
                    UpcomingMoviesMapper().mapToDomain(upcomingMovie)
                }
            }
        },
        fetch = {
            repository.getUpcomingMovies()
        },
        saveFetchResult = {
            val upcomingMovies = it.results
            repository.saveUpcomingMovies(upcomingMovies)
        }
    )
}