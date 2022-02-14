package com.timkwali.tmdmovies.common.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches

import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jakewharton.espresso.OkHttp3IdlingResource
import org.hamcrest.core.StringContains.containsString
import com.timkwali.tmdmovies.R
import com.timkwali.tmdmovies.common.utils.Constants_a
import com.timkwali.tmdmovies.common.utils.FileReader
import com.timkwali.tmdmovies.common.utils.OkHttpProvider
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainActivityTest {

    @get: Rule
    var hiltAndroidRule: HiltAndroidRule? = HiltAndroidRule(this)
    private val mockWebServer = MockWebServer()

    @Before
    @Throws(IOException::class)
    fun setup() {
        mockWebServer.start(8080)
        IdlingRegistry.getInstance().register(
            OkHttpProvider.okHttpClient?.let {
                OkHttp3IdlingResource.create(
                    "okhttp",
                    it
                )
            }
        )
    }

    @After
    @Throws(IOException::class)
    fun teardown() {
        mockWebServer.shutdown()
    }

    private fun setUpMockWebServer(code: Int, body: String?) {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(recordedRequest: RecordedRequest): MockResponse {
                var mockResponse: MockResponse? = null
                try {
                    mockResponse = MockResponse()
                        .setResponseCode(code)
                        .setBody(Objects.requireNonNull(FileReader.readStringFromFile(body)))
                } catch (exception: IOException) {
                    exception.printStackTrace()
                }
                return mockResponse!!
            }
        }
    }

    @Test
    fun successful_response_shows_all_movies_recyclerViews() {
        setUpMockWebServer(Constants_a.successCode, "latest_movies_response.json")
        setUpMockWebServer(Constants_a.successCode, "popular_movies_response.json")
        setUpMockWebServer(Constants_a.successCode, "upcoming_movies_response.json")

        ActivityScenario.launch(MainActivity::class.java)

        onView(ViewMatchers.withId(R.id.movies_categories_fragment))
            .check(matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.latest_movies_rv))
            .check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(ViewMatchers.withId(R.id.popular_movies_rv))
            .check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(ViewMatchers.withId(R.id.upcoming_movies_rv))
            .check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun recyclerView_successfully_shows_popular_movies() {
        setUpMockWebServer(Constants_a.successCode, "popular_movies_response.json")

        ActivityScenario.launch(MainActivity::class.java)

        onView(ViewMatchers.withId(R.id.popular_movies_rv))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    ViewActions.click()
                )
            )
        onView(ViewMatchers.withId(R.id.movie_details_fragment))
            .check(matches(ViewMatchers.isDisplayed()))

        onView(ViewMatchers.withId(R.id.movie_name_tv))
            .check(matches(ViewMatchers.withText("Ghostbusters: Afterlife")))

        onView(ViewMatchers.withId(R.id.back_btn))
            .perform(
                ViewActions.click()
            )
        onView(ViewMatchers.withId(R.id.movies_categories_fragment))
            .check(matches(ViewMatchers.isDisplayed()))
    }


    @Test
    fun check_if_movies_list_recyclerView_is_shown() {
        setUpMockWebServer(Constants_a.successCode, "popular_movies_response.json")

        ActivityScenario.launch(MainActivity::class.java)

        onView(ViewMatchers.withId(R.id.popular_movies_more_btn))
            .perform(
                ViewActions.click()
            )
        onView(ViewMatchers.withId(R.id.movies_list_fragment))
            .check(matches(ViewMatchers.isDisplayed()))

        onView(ViewMatchers.withId(R.id.movies_list_rv))
            .check(matches(ViewMatchers.isDisplayed()))
    }
}