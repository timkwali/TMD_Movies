package com.timkwali.tmdmovies.moviescategories.data.mappers

import com.timkwali.tmdmovies.common.data.model.popularmovies.PopularMovie
import com.timkwali.tmdmovies.common.utils.DomainMapper
import com.timkwali.tmdmovies.moviescategories.domain.model.Movie

class PopularMoviesMapper: DomainMapper<PopularMovie, Movie> {
    override fun mapToDomain(entity: PopularMovie): Movie {
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