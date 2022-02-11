package com.timkwali.tmdmovies.common.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.timkwali.tmdmovies.common.data.api.MoviesApi
import com.timkwali.tmdmovies.common.data.cache.MoviesDatabase
import com.timkwali.tmdmovies.common.data.model.popularmovies.PopularMovie
import com.timkwali.tmdmovies.common.data.model.remotekeys.MoviesRemoteKeys
import com.timkwali.tmdmovies.common.utils.MovieType
import javax.inject.Inject

@ExperimentalPagingApi
class MoviesRemoteMediator @Inject constructor(
    private val movieType: MovieType,
    private val moviesApi: MoviesApi,
    private val moviesDatabase: MoviesDatabase
): RemoteMediator<Int, MovieType>() {

    private val moviesDao = moviesDatabase.moviesDao()
    private val moviesRemoteKeysDao = moviesDatabase.moviesRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieType>
    ): MediatorResult {

    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieType>
    ): MoviesRemoteKeys? {
        return when(movieType) {
            is MovieType.Popular -> {
                state.anchorPosition?.let { position ->
                    state.closestItemToPosition(position)?.popularMovie?.id?.let { id ->
                        moviesRemoteKeysDao.getRemoteKeys(id = id)
                    }
                }
            }
        }

//        return state.anchorPosition?.let { position ->
//            state.closestItemToPosition(position)?.id?.let { id ->
//                moviesRemoteKeysDao.getRemoteKeys(id = id)
//            }
//        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MovieType>
    ): MoviesRemoteKeys? {
        return when(movieType) {
            is MovieType.Popular -> {
                state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
                    ?.let { movieType ->
                        movieType.popularMovie.id?.let { moviesRemoteKeysDao.getRemoteKeys(id = it) }
                    }
            }
        }

//        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
//            ?.let { newsArticle ->
//                newsArticleRemoteKeysDao.getRemoteKeys(id = newsArticle.id)
//            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MovieType>
    ): MoviesRemoteKeys? {

        return when(movieType) {
            is MovieType.Popular -> {
                state.pages.lastOrNull(){ it.data.isNotEmpty() }?.data?.lastOrNull()
                    ?.let { movieType ->
                        movieType.popularMovie.id?.let { moviesRemoteKeysDao.getRemoteKeys(it) }
                    }
            }
        }

//        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
//            ?.let { newsArticle ->
//                newsArticleRemoteKeysDao.getRemoteKeys(id = newsArticle.id)
//            }
    }
}