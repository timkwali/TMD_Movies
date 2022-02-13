package com.timkwali.tmdmovies.movieslist.presenter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.timkwali.tmdmovies.common.domain.usecases.GetGenres
import com.timkwali.tmdmovies.common.utils.MovieType
import com.timkwali.tmdmovies.common.utils.Constants
import com.timkwali.tmdmovies.common.utils.DataStates
import com.timkwali.tmdmovies.common.utils.Utils.getCategoriesData
import com.timkwali.tmdmovies.common.utils.Utils.getGenresData
import com.timkwali.tmdmovies.movieslist.domain.usecase.GetMoviesList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesListViewModelTest {

    @get: Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var moviesListViewModel: MoviesListViewModel
    private lateinit var getMoviesList: GetMoviesList
    private lateinit var getGenres: GetGenres

    private fun initialise(movieType: MovieType, dataStates: DataStates) {
        val categoriesData = getCategoriesData(dataStates)
        val genresData = getGenresData(dataStates)

        getMoviesList = Mockito.mock(GetMoviesList::class.java)
        Mockito.`when`(getMoviesList(movieType)).thenReturn(categoriesData)

        getGenres = Mockito.mock(GetGenres::class.java)
        Mockito.`when`(getGenres()).thenReturn(genresData)

        moviesListViewModel = MoviesListViewModel(getMoviesList, getGenres)
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
    fun `get latest movies returns list of movies`() {
        runTest {
            //Given
            initialise(MovieType.LATEST, DataStates.SUCCESS)

            //When
            moviesListViewModel.getMovies(MovieType.LATEST).join()

            //Then
            val moviesList = moviesListViewModel.moviesList.value?.data
            assert(moviesList == Constants.moviesList)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get popular movies returns list of movies`() {
        runTest {
            //Given
            initialise(MovieType.POPULAR, DataStates.SUCCESS)

            //When
            moviesListViewModel.getMovies(MovieType.POPULAR).join()

            //Then
            val moviesList = moviesListViewModel.moviesList.value?.data
            assert(moviesList == Constants.moviesList)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get upcoming movies returns list of movies`() {
        runTest {
            //Given
            initialise(MovieType.UPCOMING, DataStates.SUCCESS)

            //When
            moviesListViewModel.getMovies(MovieType.UPCOMING).join()

            //Then
            val moviesList = moviesListViewModel.moviesList.value?.data
            assert(moviesList == Constants.moviesList)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get genres returns list of genres`() {
        runTest {
            //Given
            initialise(MovieType.LATEST, DataStates.SUCCESS)

            //When
            moviesListViewModel.getMoviesGenres().join()

            //Then
            val genresList = moviesListViewModel.moviesGenres.value?.data
            assert(genresList == Constants.genresList)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `failed movie request returns error message`() {
        runTest {
            //Given
            initialise(MovieType.LATEST, DataStates.ERROR)

            //When
            moviesListViewModel.getMovies(MovieType.LATEST).join()

            //Then
            val errorMessage = moviesListViewModel.moviesGenres.value?.message
            assert(errorMessage == Constants.errorMessage)
        }
    }
}