package com.timkwali.tmdmovies.common.data.mappers

import com.timkwali.tmdmovies.common.data.model.popularmovies.PopularMovie
import com.timkwali.tmdmovies.common.utils.DomainMapper
import com.timkwali.tmdmovies.common.domain.model.Movie

class PopularMoviesMapper: DomainMapper<PopularMovie, Movie> {
    override fun mapToDomain(entity: PopularMovie): Movie {
        return Movie(
            id = entity.id,
            title = entity.title,
            rating = entity.voteAverage,
            image = entity.posterPath,
            genres = entity.genreIds,
            releaseDate = entity.releaseDate,
            voteCount = entity.voteCount,
            language = entity.originalLanguage,
            description = entity.overview,
        )
    }
}