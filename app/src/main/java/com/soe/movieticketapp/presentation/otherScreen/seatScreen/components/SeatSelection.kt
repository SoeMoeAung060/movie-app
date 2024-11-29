@file:Suppress("NAME_SHADOWING")

package com.soe.movieticketapp.presentation.otherScreen.seatScreen.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soe.movieticketapp.R
import com.soe.movieticketapp.domain.model.Genre
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.presentation.common.BodyText
import com.soe.movieticketapp.presentation.common.BuyTicketButton
import com.soe.movieticketapp.presentation.common.TitleText
import com.soe.movieticketapp.presentation.otherScreen.seatScreen.SeatScreenViewModel
import com.soe.movieticketapp.util.FontSize
import com.soe.movieticketapp.util.Padding
import com.soe.movieticketapp.util.Size
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SeatSelection(
    modifier: Modifier = Modifier,
    selectedSeat: Set<Pair<Int, Int>> = emptySet(),
    onSeatSelected: (Set<Pair<Int, Int>>) -> Unit = {},
    onBuyTickets: (Movie) -> Unit,
    movie: Movie,
    viewModel: SeatScreenViewModel = hiltViewModel(),
    movieId: String,
    showtimeId: String

    ) {
    val seatStatuses by viewModel.seats.collectAsState()
    val context = LocalContext.current

    // Fetch seats when the screen is loaded
    LaunchedEffect(Unit) {
        viewModel.fetchSeats(movieId, showtimeId)
        viewModel.populateSeats(
            movieId = movie.id.toString(),
            showtimeId = showtimeId,
            rows = 8, // Number of rows in the seating grid
            columns = 8 // Number of columns in the seating grid
        )
    }

    val rows = 8
    val columnsPerSide = 4
    val roadWidth = 20.dp

    val seatPrice = 40

    //Seat grid
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        repeat(rows) { rowIndex ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                // Left side seats
                repeat(columnsPerSide) { columnIndex ->
                    val seatName = "${('A' + rowIndex)}${columnIndex + 1}"
                    val status = seatStatuses[seatName] ?: "available"
                    Seat(
                        isSelected = selectedSeat.contains(rowIndex to columnIndex),
                        isReserved = status == "reserved",
                        onClick = {
                            val updatedSeat = if (selectedSeat.contains(rowIndex to columnIndex)) {
                                selectedSeat - (rowIndex to columnIndex) // Deselect
                            } else {
                                selectedSeat + (rowIndex to columnIndex) // Select
                            }

                            onSeatSelected(updatedSeat)
                        }
                    )

                }
                // Road (aisle) in the middle
                Spacer(modifier = Modifier.width(roadWidth))

                // Right side seats
                repeat(columnsPerSide) { columnIndex ->
                    val seatName = "${('A' + rowIndex)}${columnIndex + 1}"
                    val status = seatStatuses[seatName] ?: "available"
                    Seat(
                        isSelected = selectedSeat.contains(rowIndex to (columnsPerSide + columnIndex)),
                        isReserved = status == "reserved",
                        onClick = {
                            val updatedSeat = if (selectedSeat.contains(rowIndex to (columnsPerSide + columnIndex))) {
                                selectedSeat - (rowIndex to (columnsPerSide + columnIndex)) // Deselect
                            } else {
                                selectedSeat + (rowIndex to (columnsPerSide + columnIndex)) // Select
                            }
                            onSeatSelected(updatedSeat)
                        }
                    )

                }
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Padding.Medium),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DisplaySeatAvailable(
                text = "Available",
                selectedColor = MaterialTheme.colorScheme.background,
                borderColor = Color(0xff1A38A1)
            )

            DisplaySeatAvailable(
                text = "Reserved",
                selectedColor = Color(0xff162E84),
                borderColor = Color(0xff162E84)
            )

            DisplaySeatAvailable(
                text = "Selected",
                selectedColor = Color(0xff4AD0EE),
                borderColor = Color(0xff4AD0EE)
            )


        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Padding.Large),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                BodyText(
                    modifier = Modifier.padding(bottom = Padding.Small),
                    text = "${selectedSeat.size} ${stringResource(R.string.seat)}",
                    fontSize = FontSize.SemiMedium,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )

                TitleText(
                    text = "$ ${selectedSeat.size * seatPrice}",
                    fontSize = FontSize.Title,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            BuyTicketButton(
                onClick = { selectedMovie ->
                    val selectedSeats = selectedSeat.map { (row, col) -> "${('A' + row)}${col + 1}" }
                    CoroutineScope(Dispatchers.IO).launch {
                        val isValid = viewModel.validateSeats(movieId, showtimeId, selectedSeats)
                        if (isValid) {
                            selectedSeats.forEach { seat ->
                                viewModel.updateSeatStatus(movieId, showtimeId, seat, "reserved")
                            }
                            withContext(Dispatchers.Main) {
                                onBuyTickets(selectedMovie)
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "Some seats are no longer available.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                },
                text = "Buy Now",
                movie = movie

            )
        }

    }
}


@Composable
fun Seat(
    isSelected: Boolean,
    isReserved : Boolean,
    onClick: () -> Unit
) {
    val color = when {
        isReserved -> {
            Color(0xff162E84)
        }
        isSelected -> {
            Color(0xff4AD0EE)
        }
        else -> MaterialTheme.colorScheme.background
    }

        Box(
            modifier = Modifier
                .size(40.dp)
                .padding(4.dp)
                .background(
                    color = color,
                    shape = RoundedCornerShape(8.dp)
                )
                .border(1.dp, Color(0xff1A38A1), RoundedCornerShape(8.dp))
                .clickable(enabled = !isReserved) { onClick() } // Disable click for reserved seats
        )
}



@Composable
fun DisplaySeatAvailable(
    modifier: Modifier = Modifier,
    text : String,
    selectedColor: Color,
    borderColor: Color
) {

    Row(
        modifier = modifier.padding(Padding.Small),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(end = Padding.Small)
                .size(Size.IconSizeExtraSmall)
                .background(
                    color = selectedColor,
                    shape = RoundedCornerShape(4.dp)
                )
                .border(1.dp, borderColor, RoundedCornerShape(4.dp)),
        )

        BodyText(
            text = text,
            fontSize = FontSize.SemiMedium,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
            color = MaterialTheme.colorScheme.onBackground
        )
    }

}


@Preview(showBackground = true)
@Composable
private fun DisplaySeatAvailablePreview() {
    MovieTicketAppTheme {
        DisplaySeatAvailable(
            text = "Available",
            selectedColor = Color(0xff4AD0EE),
            borderColor = Color(0xff1A38A1)
        )
    }
    
}

@Preview(showBackground = true)
@Composable
private fun SeatSelectionFunctionPreview() {
    MovieTicketAppTheme {
        SeatSelection(
            onSeatSelected = {},
            onBuyTickets = {},
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
            movieId = "1",
            showtimeId = "1"
        )
    }

}