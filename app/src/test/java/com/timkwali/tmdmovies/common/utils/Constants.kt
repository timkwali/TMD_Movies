package com.timkwali.tmdmovies.common.utils

import com.timkwali.tmdmovies.common.data.cache.dao.MoviesDao
import com.timkwali.tmdmovies.common.data.model.genres.Genre
import com.timkwali.tmdmovies.common.data.model.genres.Genres
import com.timkwali.tmdmovies.common.data.model.latestmovies.LatestMoviesResponse
import com.timkwali.tmdmovies.common.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object Constants {
    val moviesList: List<Movie> = listOf(
        Movie(id = 425909, title = "Ghostbusters", rating = 5.6, image = "/sg4xJaufDiQl7caFEskBtQXfD4x", genres = listOf(14, 35, 12), releaseDate = "2021-11-11", voteCount = 1934, language = "en", description = "When a single mom and her two kids arrive in a small town, they begin to discover their connection to the original Ghostbusters and the secret legacy their grandfather left behind."),
        Movie(id = 438695, title = "Sing 2", rating = 8.2, image = "/aWeKITRFbbwY8txG5uCj4rMCfSP.jpg", genres = listOf(35, 16, 10751, 10402), releaseDate = "2021-12-01", voteCount = 1803, language = "en", description = "Buster and his new cast now have their sights set on debuting a new show at the Crystal Tower Theater in glamorous Redshore City. But with no connections, he and his singers must sneak into the Crystal Entertainment offices, run by the ruthless wolf mogul Jimmy Crystal, where the gang pitches the ridiculous idea of casting the lion rock legend Clay Calloway in their show. Buster must embark on a quest to find the now-isolated Clay and persuade him to return to the stage.",),
        Movie(id = 460458, title = "Resident Evil: Welcome to Raccoon City", rating = 6.1, image = "/7uRbWOXxpWDMtnsd2PF3clu65jc.jpg", genres = listOf(27, 28, 878), releaseDate = "2021-11-24", voteCount = 1173, language = "en", description = "Once the booming home of pharmaceutical giant Umbrella Corporation, Raccoon City is now a dying Midwestern town. The company’s exodus left the city a wasteland…with great evil brewing below the surface. When that evil is unleashed, the townspeople are forever…changed…and a small group of survivors must work together to uncover the truth behind Umbrella and make it through the night.",),
    )
    val genresList = listOf(
        Genre(id = 12, name = "Adventure"),
        Genre(id = 14, name = "Fantasy"),
        Genre(id = 16, name = "Animation"),
        Genre(id = 18, name = "Drama"),
        Genre(id = 27, name = "Horror"),
        Genre(id = 28, name = "Action"),
        Genre(id = 35, name = "Comedy"),
        Genre(id = 36, name = "History"),
        Genre(id = 878, name = "Science Fiction"),
        Genre(id = 10751, name = "War"),
    )
    const val errorMessage = "Something went wrong!"

    val movieLoadingData: Flow<Resource<List<Movie>>> = flowOf(Resource.Loading(moviesList))
    val movieSuccessData: Flow<Resource<List<Movie>>> = flowOf(Resource.Success(moviesList))
    val movieErrorData: Flow<Resource<List<Movie>>> = flowOf(Resource.Error(errorMessage, null))

    val genreLoadingData: Flow<Resource<List<Genre>>> = flowOf(Resource.Loading(genresList))
    val genreSuccessData: Flow<Resource<List<Genre>>> = flowOf(Resource.Success(genresList))
    val genreErrorData: Flow<Resource<List<Genre>>> = flowOf(Resource.Error(errorMessage, null))

    val latestMoviesResponse = LatestMoviesResponse(
        4, false, "", "", 5000000, listOf(12, 14, 45), "https://vimeo.com/658826568",
        "tt18181952", "en", "Late", "\"Charlie (Ewan Gordon) , a student who suffers from anxiety finds out that he's going to be late for a presentation he forgot about.",
        2000, "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg", listOf(), listOf(), "2021-12-15", 15000000, 6, "Released",
        "A story about anxiety, and how fast it can take over in a moment of panic", "Late", false, 8.3, 200
    )

    val latestMoviesResponseTitle = latestMoviesResponse.title

    val latestMoviesFlow: Flow<List<LatestMoviesResponse>> = flowOf(
        listOf(
            latestMoviesResponse, latestMoviesResponse, latestMoviesResponse, latestMoviesResponse, latestMoviesResponse
        )
    )

    val getGenresResponse = Genres(genresList)

    val getDbGenresResult = flowOf(genresList)
}