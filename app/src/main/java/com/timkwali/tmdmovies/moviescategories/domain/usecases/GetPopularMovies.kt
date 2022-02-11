package com.timkwali.tmdmovies.moviescategories.domain.usecases

import com.timkwali.tmdmovies.common.domain.repository.MoviesRepository
import com.timkwali.tmdmovies.moviescategories.data.mappers.PopularMoviesMapper
import com.timkwali.tmdmovies.moviescategories.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPopularMovies @Inject constructor(
    private val repository: MoviesRepository
) {

    operator fun invoke(): Flow<List<Movie>> {
        return flow {
            emit(
                repository.getPopularMovies().results
                    .map {
                        PopularMoviesMapper().mapToDomain(it)
                    }
            )
        }
    }
}