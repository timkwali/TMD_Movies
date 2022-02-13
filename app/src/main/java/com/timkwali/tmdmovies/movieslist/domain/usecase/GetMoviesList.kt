package com.timkwali.tmdmovies.movieslist.domain.usecase

import com.timkwali.tmdmovies.common.domain.model.Movie
import com.timkwali.tmdmovies.common.domain.usecases.GetLatestMovies
import com.timkwali.tmdmovies.common.domain.usecases.GetPopularMovies
import com.timkwali.tmdmovies.common.domain.usecases.GetUpcomingMovies
import com.timkwali.tmdmovies.common.utils.MovieType
import com.timkwali.tmdmovies.common.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesList @Inject constructor(
    private val getLatestMovies: GetLatestMovies,
    private val getPopularMovies: GetPopularMovies,
    private val getUpcomingMovies: GetUpcomingMovies
) {

    operator fun invoke(movieType: MovieType): Flow<Resource<List<Movie>>> {
        return when(movieType) {
            MovieType.LATEST -> getLatestMovies()
            MovieType.POPULAR -> getPopularMovies()
            MovieType.UPCOMING -> getUpcomingMovies()
        }
    }
}