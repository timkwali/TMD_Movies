package com.timkwali.tmdmovies.moviescategories.data.mappers

import com.timkwali.tmdmovies.common.data.model.popularmovies.PopularMovie
import com.timkwali.tmdmovies.common.data.model.upcomingmovies.UpcomingMovie
import com.timkwali.tmdmovies.common.utils.DomainMapper
import com.timkwali.tmdmovies.moviescategories.domain.model.Movie

class UpcomingMoviesMapper: DomainMapper<UpcomingMovie, Movie> {
    override fun mapToDomain(entity: UpcomingMovie): Movie {
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