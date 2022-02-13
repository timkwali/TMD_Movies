package com.timkwali.tmdmovies.common.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.timkwali.tmdmovies.common.data.model.genres.Genre
import com.timkwali.tmdmovies.common.domain.repository.MoviesRepository
import com.timkwali.tmdmovies.common.utils.Constants
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
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetGenresTest {

    private lateinit var getGenres: GetGenres
    private lateinit var repository: MoviesRepository

    @get: Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private val testDispatcher = StandardTestDispatcher()

    private suspend fun initialise() {
        repository = Mockito.mock(MoviesRepository::class.java)
        Mockito.`when`(repository.getGenres()).thenReturn(Constants.getGenresResponse)
        Mockito.`when`(repository.getDbGenres()).thenReturn(Constants.getDbGenresResult)

        getGenres = GetGenres(repository)
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get genre use case returns resource list genre flow`() {
        runTest {
            //Given
            initialise()

            //When
            var genresList: List<Genre> = emptyList()
            getGenres().collect {
                genresList = it.data!!
            }

            //Then
            assert(genresList ==  Constants.genresList)
        }
    }
}