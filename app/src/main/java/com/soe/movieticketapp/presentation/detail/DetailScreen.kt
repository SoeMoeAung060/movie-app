@file:Suppress("NAME_SHADOWING")
@file:kotlin.OptIn(ExperimentalMaterial3Api::class)

package com.soe.movieticketapp.presentation.detail

import android.os.Build
import android.util.Log
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import com.soe.movieticketapp.R
import com.soe.movieticketapp.domain.model.Crew
import com.soe.movieticketapp.domain.model.Genre
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.presentation.common.BuyTicketButton
import com.soe.movieticketapp.presentation.detail.components.DetailMovieTopBar
import com.soe.movieticketapp.presentation.detail.components.DetailScreenUi
import com.soe.movieticketapp.util.MovieType
import com.soe.movieticketapp.util.TopBarHeaderTitle
import com.soe.movieticketapp.util.openWatchLink
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    popUp: () -> Unit,
    detailViewModel: DetailScreenViewModel = hiltViewModel(),
    movieType: MovieType,
    movie: Movie,
    navigateToDetailScreen: (Movie) -> Unit,
    navigateToCastAndCrewScreen: (Movie) -> Unit,
    navigateToSeatScreen: () -> Unit
) {


    val context = LocalContext.current
    val movie by remember { mutableStateOf(movie) }
    val movieType = remember { movieType }


    val getCastMovie = detailViewModel.getCastMovie.value
    val getCrewMovie = detailViewModel.getCrewMovie.value
    val trailerList = detailViewModel.getTrailerMovie.value
    val getDetailMovie = detailViewModel.getDetailMovie.value


    LaunchedEffect(true) {
        detailViewModel.getDetailMovies(movie.id, movieType)
        detailViewModel.getWatchProvider(movie.id, movieType)

        detailViewModel.getCastAndCrew(movie.id, movieType)
        detailViewModel.getSimilarMoviesAndTvSeries(movie.id, movieType)
        detailViewModel.getTrailerMovies(movie.id, movieType)
    }



    Scaffold(
        topBar = {
            DetailMovieTopBar(
                onBackClick = popUp
            )
        }
    ) {
        if (getDetailMovie != null) {
            DetailScreenUi(
                modifier = modifier.padding(it),
                movie = movie,
                detail = getDetailMovie,
                cast = getCastMovie,
                crew = getCrewMovie,
                navigateToDetailScreen = navigateToDetailScreen,
                navigateToCastAndCrewScreen = navigateToCastAndCrewScreen,
                onClickTrailer = {
                    Log.d("DetailScreen", "Opening trailer link")
                    val trailer = trailerList?.firstOrNull { it.type == "Trailer" }
                    if (trailer != null) {
                        val trailerUrl = "https://www.youtube.com/watch?v=${trailer.key}"
                        Log.d("DetailScreen", "Opening trailer URL: $trailerUrl")
                        openWatchLink(context, trailerUrl) // Open the trailer link
                    } else {
                        Log.d("DetailScreen", "No trailer found.") // Log if no trailer is found
                    }
                },
                onClickBuyTicket = navigateToSeatScreen
            )
        }



    }


}



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DetailScreenPreview(modifier: Modifier = Modifier) {

    MovieTicketAppTheme {
        DetailScreen(
            movieType = MovieType.MOVIE,
            movie = Movie(
                adult = false,
                backdropPath = "/path/to/backdrop2.jpg",
                posterPath = "/path/to/poster2.jpg",
                genreIds = listOf(16, 35),
                genres = listOf(Genre(3, "Animation"), Genre(4, "Comedy")),
                mediaType = "movie",
                firstAirDate = "2023-02-02",
                id = 2,
                imdbId = "tt7654321",
                originalLanguage = "en",
                originalName = "Original Name 2",
                overview = "This is the overview of the second movie.",
                popularity = 9.0,
                releaseDate = "2023-02-02",
                runtime = 90,
                title = "Movie Title 2",
                video = true,
                voteAverage = 8.0,
                voteCount = 200,
            ),
            popUp = {},
            navigateToDetailScreen = {},
            navigateToCastAndCrewScreen ={},
            navigateToSeatScreen = {}
        )
    }

}