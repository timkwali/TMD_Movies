package com.timkwali.tmdmovies.common.utils

import com.timkwali.tmdmovies.common.data.model.popularmovies.PopularMovie

sealed class MovieType(
    val popularMovie: PopularMovie,
) {
    class Popular(popularMovie: PopularMovie): MovieType(popularMovie)
}
