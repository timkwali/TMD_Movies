package com.timkwali.tmdmovies.common.data.repository

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.timkwali.tmdmovies.common.data.api.MoviesApi
import com.timkwali.tmdmovies.common.data.cache.MoviesDatabase
import com.timkwali.tmdmovies.common.data.cache.dao.MoviesDao
import com.timkwali.tmdmovies.common.domain.model.Movie
import com.timkwali.tmdmovies.common.domain.repository.MoviesRepository
import com.timkwali.tmdmovies.common.utils.Constants
import com.timkwali.tmdmovies.common.utils.DataStates
import com.timkwali.tmdmovies.common.utils.MovieType
import com.timkwali.tmdmovies.movieslist.domain.usecase.GetMoviesList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesRepositoryImplTest {

    @get: Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var moviesRepository: MoviesRepositoryImpl
    private lateinit var moviesApi: MoviesApi
    private lateinit var moviesDao: MoviesDao
    private lateinit var moviesDatabase: MoviesDatabase

    private suspend fun initialise() {
        moviesApi =  Mockito.mock(MoviesApi::class.java)
        Mockito.`when`(moviesApi.getLatestMovies()).thenReturn(Constants.latestMoviesResponse)

        moviesDao = Mockito.mock(MoviesDao::class.java)
        Mockito.`when`(moviesDao.getLatestMovies()).thenReturn(Constants.latestMoviesFlow)
        moviesDatabase = Mockito.mock(MoviesDatabase::class.java)
        Mockito.`when`(moviesDatabase.moviesDao()).thenReturn(moviesDao)

        moviesRepository = MoviesRepositoryImpl(moviesApi, moviesDatabase)
    }

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get api latest movies returns latest movies response`() {
        runTest {
            //Given
            initialise()

            //When
            val moviesList = moviesRepository.getLatestMovies()

            //Then
            assert(moviesList.title == Constants.latestMoviesResponseTitle)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get database latest movies returns latest movies response flow`() {
        runTest {
            //Given
            initialise()

            //When
            var movieTitle = ""
             moviesRepository.getDbLatestMovies().collect {
                movieTitle = it[0].title.toString()
            }

            //Then
            assert(movieTitle == Constants.latestMoviesResponseTitle)
        }
    }

}