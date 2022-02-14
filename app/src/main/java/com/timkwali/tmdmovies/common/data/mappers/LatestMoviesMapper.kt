package com.timkwali.tmdmovies.common.data.mappers

import com.timkwali.tmdmovies.common.data.model.latestmovies.LatestMoviesResponse
import com.timkwali.tmdmovies.common.utils.DomainMapper
import com.timkwali.tmdmovies.common.domain.model.Movie

class LatestMoviesMapper: DomainMapper<LatestMoviesResponse, Movie> {
    override fun mapToDomain(entity: LatestMoviesResponse): Movie {
        return Movie(
            id = entity.id,
            title = entity.title,
            rating = entity.voteAverage,
            image = entity.posterPath,
            genres = emptyList(),
            releaseDate = entity.releaseDate,
            voteCount = entity.voteCount,
            language = entity.originalLanguage,
            description = entity.overview,
        )
    }
}