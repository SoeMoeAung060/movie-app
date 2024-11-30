package com.soe.movieticketapp.presentation.otherScreen.ticketScreen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soe.movieticketapp.R
import com.soe.movieticketapp.domain.model.Detail
import com.soe.movieticketapp.domain.model.Genre
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.presentation.common.BodyText
import com.soe.movieticketapp.presentation.common.CheckoutMovieImage
import com.soe.movieticketapp.presentation.common.RatingBar
import com.soe.movieticketapp.presentation.common.RatingText
import com.soe.movieticketapp.presentation.common.TitleText
import com.soe.movieticketapp.util.BASE_POSTER_IMAGE_URL
import com.soe.movieticketapp.util.FontSize
import com.soe.movieticketapp.util.MovieType
import com.soe.movieticketapp.util.Padding
import com.soe.movieticketapp.util.Spacing
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme




@Composable
fun TicketScreen(
    modifier: Modifier = Modifier,
    date: String,
    time: String,
    seats: String,
    movie: Movie,
    movieType: MovieType,
    popUp:() -> Unit,
    viewModel: TicketScreenViewModel = hiltViewModel()
) {

    val getDetailMovie = viewModel.getDetailMovie.value

    LaunchedEffect(true) {
        viewModel.getDetailMovies(movieId = movie.id, movieType = movieType)
    }

    if (getDetailMovie != null) {
        TicketScreenContent(
            modifier = modifier,
            date = date,
            time = time,
            seats = seats,
            movie = movie,
            detail = getDetailMovie,
            popUp = popUp
        )
    }
}


@Composable
fun TicketScreenContent(
    date: String,
    time: String,
    seats: String,
    modifier: Modifier = Modifier,
    movie: Movie,
    detail: Detail,
    popUp:() -> Unit
) {

    val context = LocalContext.current


    Scaffold(
        topBar = {
            TicketTopBar(
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
            ){
                Box(
                    modifier = modifier
                        .width(350.dp)
                        .height(500.dp)
                        .padding(Padding.Medium)
                        .clip(RoundedCornerShape(Padding.Medium))
                        .background(colorResource(R.color.DarkBlue))
                        .align(Alignment.Center),
                ){

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
                        ){
                            CheckoutMovieImage(
                                imageUrl = "$BASE_POSTER_IMAGE_URL${movie.posterPath}",
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
                                    text = detail.genres.joinToString(", ") {genre -> genre.name },
                                    fontSize = FontSize.Medium,
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                                )

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = Padding.Small),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    RatingBar(
                                        modifier = Modifier.padding(),
                                        rating = (movie.voteAverage?.div(2)),
                                        starsColor = Color.Yellow
                                    )

                                    RatingText(
                                        modifier = Modifier.padding(),
                                        rating = movie.voteAverage,
                                        color = Color.Yellow
                                    )
                                }

                            }
                        }

                        Spacer(Modifier.height(Spacing.Medium))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                modifier = Modifier.padding(top = 2.dp, end = Padding.Small),
                                painter = painterResource(R.drawable.info_circle),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                            )
                            BodyText(
                                text = stringResource(R.string.alart),
                                fontSize = FontSize.SemiMedium,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                            )
                        }

                        Spacer(Modifier.height(Spacing.Medium))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                        ){
                            Box(
                                modifier = Modifier
                                .fillMaxWidth()
                                .height(14.dp)
                                .clip(CircleShape)
                                .background(brush = Brush.verticalGradient(
                                    0.0f to MaterialTheme.colorScheme.background.copy(alpha = 0.3f),
                                    1f to MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
                                    startY = 0.0f,
                                    endY = 50.0f
                                ))
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                                    .padding(Padding.Small)
                                    .align(Alignment.TopCenter)
                                    .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
                                    .background(Color(0xFF153E64)),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top
                            ){
                                //Date , Time, Studio and Seat
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(Padding.Medium),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    TicketContent(
                                        text = stringResource(R.string.date),
                                        checkoutText = date
                                    )

                                    TicketContent(
                                        text = stringResource(R.string.time),
                                        checkoutText = time
                                    )
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = Padding.Medium),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    TicketContent(
                                        text = stringResource(R.string.studio),
                                        checkoutText = stringResource(R.string.studio_name),
                                    )

                                    TicketContent(
                                        text = stringResource(R.string.seat),
                                        checkoutText = seats
                                    )
                                }

//
                                Spacer(Modifier.height(Spacing.Medium))
//
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f))
                                )
//
//                                Spacer(Modifier.height(Spacing.Small))

                                Column(
                                    modifier = Modifier.weight(0.2f),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ){
                                    Image(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(50.dp),
                                        painter = painterResource(R.drawable.barcode),
                                        contentDescription = null
                                    )
                                }


                            }



                        }


                    }
                }
            }

        }
    }

}


@Composable
fun TicketContent(
    modifier: Modifier = Modifier,
    text : String,
    checkoutText : String
) {

    Column(
        modifier = modifier,
        horizontalAlignment = if (text == "Time" || text == "Seat") Alignment.End else Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        TitleText(
            text = text,
            fontSize = FontSize.Medium,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal),
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)

        )

        TitleText(
            text = checkoutText,
            fontSize = FontSize.Medium,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal),
            color = MaterialTheme.colorScheme.onBackground

        )
    }

}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES )
@Composable
private fun CheckoutScreenPreview() {
    MovieTicketAppTheme {
        TicketScreenContent(
            date = "2023-11-20",
            time = "10:00 AM",
            seats = "A1, B2",
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
            popUp = {}
        )
    }

}
