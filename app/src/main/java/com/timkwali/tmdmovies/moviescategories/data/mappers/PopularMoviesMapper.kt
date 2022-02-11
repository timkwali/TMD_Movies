package com.timkwali.tmdmovies.moviescategories.data.mappers

import com.timkwali.tmdmovies.common.data.model.popularmovies.Result
import com.timkwali.tmdmovies.common.utils.DomainMapper
import com.timkwali.tmdmovies.moviescategories.domain.model.Movie

class PopularMoviesMapper: DomainMapper<Result, Movie> {
    override fun mapToDomain(entity: Result): Movie {
        return Movie(
            id = entity.id,
            title = entity.title,
            rating = entity.voteAverage,
            image = entity.posterPath,
            genres = entity.genreIds,
            releaseDate = entity.releaseDate,
            language = entity.originalLanguage,
            description = entity.overview,
        )
    }
}