package com.soe.movieticketapp.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soe.movieticketapp.domain.model.Detail
import com.soe.movieticketapp.domain.model.Genre
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme

@Composable
fun BuyTicketButton(
    modifier: Modifier = Modifier,
    movie: Movie,
    onClick: (Movie) -> Unit,
    text : String
) {
    Button(
        onClick = { onClick(movie) } ,
        modifier = modifier
            .width(150.dp)
            .height(48.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.background
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
private fun BottomTicketMoviePreview() {
    MovieTicketAppTheme {
        BuyTicketButton(
            onClick = {},
            text = "Buy Ticket",
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