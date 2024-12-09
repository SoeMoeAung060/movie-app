package com.soe.movieticketapp.presentation.otherScreen.checkoutScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.soe.movieticketapp.R
import com.soe.movieticketapp.domain.model.Detail
import com.soe.movieticketapp.domain.model.Genre
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.navigation.MovieNavController
import com.soe.movieticketapp.presentation.common.CheckoutMovieImage
import com.soe.movieticketapp.presentation.common.RatingBar
import com.soe.movieticketapp.presentation.common.RatingText
import com.soe.movieticketapp.presentation.common.TitleText
import com.soe.movieticketapp.presentation.common.TopBarWithPopUpScreen
import com.soe.movieticketapp.presentation.otherScreen.seatScreen.SeatScreenViewModel
import com.soe.movieticketapp.util.BASE_POSTER_IMAGE_URL
import com.soe.movieticketapp.util.FontSize
import com.soe.movieticketapp.util.MovieType
import com.soe.movieticketapp.util.Padding
import com.soe.movieticketapp.util.Spacing
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme
import com.soe.movieticketapp.util.ui.theme.blue02
import com.soe.movieticketapp.util.ui.theme.darkNavy


@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    date: String,
    time: String,
    seats: String,
    price: String,
    movie: Movie,
    movieType: MovieType,
    popUp: () -> Unit,
    viewModel: SeatScreenViewModel = hiltViewModel(),
    movieNavController: MovieNavController
) {

    val getDetailMovie = viewModel.getDetailMovie.value

    LaunchedEffect(true) {
        viewModel.getDetailMovies(movieId = movie.id, movieType = movieType)
    }

    if (getDetailMovie != null) {
        CheckoutScreenContent(
            modifier = modifier,
            date = date,
            time = time,
            seats = seats,
            price = price,
            movie = movie,
            detail = getDetailMovie,
            popUp = popUp,
            movieNavController = movieNavController,
            viewModel = viewModel
        )
    }
}


@Composable
fun CheckoutScreenContent(
    date: String,
    time: String,
    seats: String,
    price: String,
    modifier: Modifier = Modifier,
    movie: Movie,
    detail: Detail,
    popUp: () -> Unit,
    movieNavController: MovieNavController,
    viewModel: SeatScreenViewModel
) {


    val context = LocalContext.current

    val seatCount = seats.split(",").size
    val tax = 3
    val totalTax = seatCount * tax
    val totalAmount = price.toInt() + totalTax

    val backgroundColor = if (isSystemInDarkTheme()) {
        darkNavy
    } else {
        blue02.copy(0.5f)
    }

    val ratingColor = if (isSystemInDarkTheme()) {
        Color.Yellow
    } else {
        DarkGray
    }

    val textColor = if (isSystemInDarkTheme()) {
        MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
    } else {
        MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
    }


    Scaffold(
        topBar = {
            TopBarWithPopUpScreen(
                onBackClick = popUp,
                text = stringResource(R.string.checkout)
            )
        }
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = it.calculateTopPadding()),
            ) {
                Box(
                    modifier = modifier
                        .width(350.dp)
                        .height(500.dp)
                        .padding(Padding.Medium)
                        .clip(RoundedCornerShape(Padding.Medium))
                        .background(backgroundColor)
                        .align(Alignment.Center),
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .align(Alignment.TopStart),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        //Movie Details
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.Top
                        ) {
                            CheckoutMovieImage(
                                imageUrl = "${BASE_POSTER_IMAGE_URL}${movie.posterPath}",
                                context = context,
                                contextDescription = "Movie Poster",
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = Padding.Medium),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                TitleText(
                                    modifier = Modifier.padding(bottom = Padding.Small),
                                    text = movie.title ?: "",
                                    fontSize = FontSize.Large,
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                                )

                                TitleText(
                                    modifier = Modifier.padding(bottom = Padding.Small),
                                    text = detail.genres.joinToString(", ") { genre -> genre.name },
                                    fontSize = FontSize.Medium,
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                                )

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = Padding.Small),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RatingBar(
                                        modifier = Modifier.padding(),
                                        rating = (movie.voteAverage?.div(2)),
                                        starsColor = ratingColor
                                    )

                                    RatingText(
                                        modifier = Modifier
                                            .padding(Padding.Small)
                                            .border(
                                                BorderStroke(
                                                    1.dp,
                                                    if (isSystemInDarkTheme()) Color.Yellow else DarkGray,
                                                )
                                            )
                                            .clip(RoundedCornerShape(Padding.Medium)),
                                        rating = movie.voteAverage,
                                        color = ratingColor
                                    )
                                }

                            }
                        }


                        //Date , Time, Studio and Seat
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = Padding.Medium),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CheckoutContent(
                                text = stringResource(R.string.date),
                                checkoutText = date
                            )

                            CheckoutContent(
                                text = stringResource(R.string.time),
                                checkoutText = time
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = Padding.Medium),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CheckoutContent(
                                text = stringResource(R.string.studio),
                                checkoutText = stringResource(R.string.studio_name),
                            )

                            CheckoutContent(
                                text = stringResource(R.string.seat),
                                checkoutText = seats
                            )
                        }


                        Spacer(Modifier.height(Spacing.Medium))


                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f))
                        )


                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = Padding.Medium),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TitleText(
                                text = if (seatCount == 1) "1 ${stringResource(R.string.ticket)}" else "$seatCount ${
                                    stringResource(
                                        R.string.tickets
                                    )
                                }",
                                fontSize = FontSize.Medium,
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal),
                                color = textColor
                            )

                            TitleText(
                                text = "$$price",
                                fontSize = FontSize.Medium,
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }


                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = Padding.Medium),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TitleText(
                                text = stringResource(R.string.tax),
                                fontSize = FontSize.Medium,
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal),
                                color = textColor
                            )

                            TitleText(
                                text = "$${seatCount * tax}",
                                fontSize = FontSize.Medium,
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        Spacer(Modifier.height(Spacing.Medium))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f))
                        )


                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = Padding.Medium),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TitleText(
                                text = stringResource(R.string.total),
                                fontSize = FontSize.Large,
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            TitleText(
                                text = "$$totalAmount",
                                fontSize = FontSize.Large,
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                    }
                }
            }


            StripePayment(
                totalAmount,
                movieNavController = movieNavController,
                date = date,
                time = time,
                seats = seats,
                price = price,
                movie = movie,
                viewModel = viewModel
            )
        }
    }

}


@Composable
fun CheckoutContent(
    modifier: Modifier = Modifier,
    text: String,
    checkoutText: String,
) {

    val textColor = if (isSystemInDarkTheme()) {
        MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
    } else {
        MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
    }

    Column(
        modifier = modifier,
        horizontalAlignment = if (text == "Time" || text == "Seat") Alignment.End else Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        TitleText(
            text = text,
            fontSize = FontSize.Medium,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal),
            color = textColor

        )

        TitleText(
            text = checkoutText,
            fontSize = FontSize.Medium,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal),
            color = MaterialTheme.colorScheme.onBackground

        )
    }

}


@Preview
@Composable
private fun CheckoutScreenPreview(
    viewModel: SeatScreenViewModel = hiltViewModel()
) {

    val navController = rememberNavController()
    val movieNavController = MovieNavController(navController)

    MovieTicketAppTheme {
        CheckoutScreenContent(
            date = "2023-11-20",
            time = "10:00 AM",
            seats = "A1, B2",
            price = "100",
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
            detail = Detail(
                posterPath = "/path/to/poster2.jpg",
                genres = listOf(Genre(3, "Animation"), Genre(4, "Comedy")),
                id = 2,
                imdbId = "tt7654321",
                originalLanguage = "en",
                overview = "This is the overview of the second movie.",
                popularity = 9.0,
                releaseDate = "2023-02-02",
                runtime = 90,
                title = "Movie Title 2",
                video = true,
                voteAverage = 8.0,
                voteCount = 200,
                budget = 1000000,
                homepage = "https://www.example.com",
                originalTitle = "Original Title 2",
                revenue = 2000000,
                tagline = "Tagline 2",
                status = "Released"
            ),
            popUp = {},
            movieNavController = movieNavController,
            viewModel = viewModel
        )
    }

}
