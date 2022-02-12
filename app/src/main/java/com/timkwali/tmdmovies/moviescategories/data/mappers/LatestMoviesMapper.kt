package com.timkwali.tmdmovies.moviescategories.data.mappers

import com.timkwali.tmdmovies.common.data.model.latestmovies.LatestMoviesResponse
import com.timkwali.tmdmovies.common.data.model.popularmovies.PopularMovie
import com.timkwali.tmdmovies.common.utils.DomainMapper
import com.timkwali.tmdmovies.moviescategories.domain.model.Movie

class LatestMoviesMapper: DomainMapper<LatestMoviesResponse, Movie> {
    override fun mapToDomain(entity: LatestMoviesResponse): Movie {
        return Movie(
            id = entity.id,
            title = entity.title,
            rating = entity.voteAverage,
            image = entity.posterPath,
            genres = entity.genres,
            releaseDate = entity.releaseDate,
            voteCount = entity.voteCount,
            language = entity.originalLanguage,
            description = entity.overview,
        )
    }
}