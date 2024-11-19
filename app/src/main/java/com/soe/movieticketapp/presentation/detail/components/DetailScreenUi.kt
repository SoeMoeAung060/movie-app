@file:Suppress("UNUSED_EXPRESSION")

package com.soe.movieticketapp.presentation.detail.components

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.soe.movieticketapp.R
import com.soe.movieticketapp.domain.model.Cast
import com.soe.movieticketapp.domain.model.Crew
import com.soe.movieticketapp.domain.model.Detail
import com.soe.movieticketapp.domain.model.Genre
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.presentation.common.HeaderMovieText
import com.soe.movieticketapp.presentation.detail.DetailScreenViewModel
import com.soe.movieticketapp.util.Padding
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailScreenUi(
    modifier: Modifier = Modifier,
    movie: Movie,
    detail: Detail,
    cast : List<Cast>,
    crew : List<Crew>,
    navigateToDetailScreen: (Movie) -> Unit,
    navigateToCastAndCrewScreen: (Movie) -> Unit,
    onClickTrailer: (Int?)->Unit,
    detailScreenViewModel: DetailScreenViewModel = hiltViewModel(),
    onClickBuyTicket: (Movie) -> Unit
) {

    val similarMovies = detailScreenViewModel.similarMoviesAndTvSeries.collectAsLazyPagingItems()

    // Debugging logs to ensure data is received
    Log.d("DetailScreen", "Movie: $movie")
    Log.d("DetailScreen", "Cast: $cast")
    Log.d("DetailScreen", "Crew: $crew")

    Box(modifier = modifier.fillMaxWidth()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {

            item {
                MovieBackdropAndPoster(
                    movie = movie,
                    onClickTrailer = onClickTrailer,
                    onClickBuyTicket = { onClickBuyTicket(movie) },
                )
            }

            item {

                Spacer(modifier = Modifier.height(Padding.Medium))

                MovieDetails(
                    movie = movie,
                    detail = detail
                )

                Spacer(modifier = Modifier.height(Padding.Medium))

                HeaderMovieText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Padding.Medium),
                    text = movie.title ?: "Unknown Title",
                )

                Spacer(modifier = Modifier.height(Padding.Medium))

                MovieOverview(
                    movie = movie,
                    crew = crew)

                Spacer(modifier = Modifier.height(Padding.Medium))

            }


            item{
                MovieCast(
                    cast = cast,
                    onClick = { navigateToCastAndCrewScreen(movie) },
                    seeMoreText = stringResource(R.string.see_more)
                )
                Spacer(modifier = Modifier.height(Padding.Medium))
            }


            item{
                SimilarMovieContent(
                    movie = similarMovies,
                    onClick = navigateToDetailScreen
                )
            }


//            item{
//                CrewDetails(crewList = crew)
//            }

        }
    }
}





@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun DetailScreenContentPreview() {
    MovieTicketAppTheme {
        DetailScreenUi(
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
                voteCount = 200
            ),
            cast = listOf(
                Cast(
                    adult = false,
                    gender = 1,
                    id = 2342,
                    department = "Acting",
                    name = "John Meillon",
                    originalName = "John Meillon",
                    popularity = 3.385,
                    profilePath = "/k9x2VwKqiTxTKP1WIgOM1AtZf2G.jpg",
                    castId = 3,
                    character = "Walter Reilly",
                    creditId = "52fe44f2c3a36847f80b3693",
                    order = 2
                )
            ),
            navigateToDetailScreen = {},
            navigateToCastAndCrewScreen = {},
            onClickTrailer = {},
            crew = listOf(

            ),
            detail = Detail(
                budget = 50000000, // Example budget in dollars
                homepage = "https://www.example.com",
                id = 12345,
                imdbId = "tt1234567",
                originalLanguage = "en",
                originalTitle = "Sample Movie Title",
                overview = "This is a sample overview of the movie. It provides a brief summary of the plot.",
                popularity = 78.9,
                posterPath = "/samplePosterPath.jpg",
                releaseDate = "2024-01-01",
                revenue = 100000000, // Example revenue in dollars
                runtime = 120, // Duration in minutes
                status = "Released",
                tagline = "This is the movie tagline",
                title = "Sample Movie",
                video = false,
                voteAverage = 7.8,
                voteCount = 1500,
                genres = listOf(
                    Genre(3, "Animation"),
                    Genre(4, "Comedy")
                )
            ),
            onClickBuyTicket = {}
        )
    }
    
}