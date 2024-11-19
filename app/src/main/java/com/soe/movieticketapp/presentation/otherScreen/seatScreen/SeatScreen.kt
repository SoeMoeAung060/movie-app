package com.soe.movieticketapp.presentation.otherScreen.seatScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soe.movieticketapp.R
import com.soe.movieticketapp.domain.model.Genre
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.presentation.common.TopBarWithPopUpScreen
import com.soe.movieticketapp.presentation.otherScreen.seatScreen.components.DateSelector
import com.soe.movieticketapp.presentation.otherScreen.seatScreen.components.SeatSelection
import com.soe.movieticketapp.presentation.otherScreen.seatScreen.components.ShowTime
import com.soe.movieticketapp.util.Padding
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme


@Composable
fun SeatScreen(
    modifier: Modifier = Modifier,
    popUp: () -> Unit,
    viewModel: SeatScreenViewModel = hiltViewModel(),
    navigateToCheckoutScreen: (
        selectedDate: String,
        selectedTime: String,
        selectedSeats: Set<Pair<Int, Int>>,
        totalPrice: String,
    ) -> Unit,
    movie: Movie
) {


    val times = listOf(
        "09:00", "12:00", "15:00", "18:00", "21:00"
    )
    val ticketPrice = 40
    val context = LocalContext.current


    val pagerState = rememberPagerState(initialPage = times.size / 2) { times.size }
    var selectedTime by remember { mutableStateOf(times[pagerState.currentPage]) }
    var selectedSeats by remember { mutableStateOf(emptySet<Pair<Int, Int>>()) }
    var selectedDate by remember { mutableStateOf("Today") } // Default to "Today"

    val purchaseState = viewModel.purchaseState

    val getDetailMovie = viewModel.getDetailMovie.value


    // Update selectedTime whenever the page changes
    LaunchedEffect(pagerState.currentPage) {
        selectedTime = times[pagerState.currentPage]
    }

    Scaffold(
        topBar = {
            TopBarWithPopUpScreen(
                onBackClick = popUp,
                text = stringResource(R.string.select_seat)
            )
        }
    ) {
        LazyColumn(
            modifier = modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {

                // Date Selector
                DateSelector(
                    onDateSelected = { date ->
                        selectedDate = date // Update the selected date
                    }
                )

                Spacer(Modifier.height(Padding.Medium))

                // Time Selector
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier,
                    contentPadding = PaddingValues(horizontal = 150.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) { index ->
                    ShowTime(
                        time = times[index],
                        selected = selectedTime == times[index]
                    )
                }


                Box(
                    modifier = Modifier.padding(top = Padding.Medium),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier
                            .padding(horizontal = Padding.Medium)
                            .fillMaxWidth()
                            .height(70.dp),
                        painter = painterResource(R.drawable.screen),
                        contentDescription = stringResource(R.string.screen)
                    )
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "SCREEN",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                // Seat Selection Grid
                SeatSelection(
                    selectedSeat = selectedSeats,
                    onSeatSelected = { newSelection ->
                        selectedSeats = newSelection
                    },
                    onBuyTickets = {
                        if (selectedSeats.isNotEmpty()) {
                            val totalPrice = selectedSeats.size * ticketPrice
                            viewModel.purchaseTicket(
                                selectedSeats = selectedSeats,
                                selectedTime = selectedTime,
                                totalPrice = totalPrice
                            )
                            navigateToCheckoutScreen(
                                selectedDate,
                                selectedTime,
                                selectedSeats,
                                totalPrice.toString(),
                            )
                        } else {
                            Toast.makeText(
                                context,
                                "Please select at lease one seat!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    movie = movie
                )
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SeatScreenPreview() {
    MovieTicketAppTheme {
        SeatScreen(
            popUp = {},
            navigateToCheckoutScreen = { _, _, _, _ -> },
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
            )
        )
    }

}