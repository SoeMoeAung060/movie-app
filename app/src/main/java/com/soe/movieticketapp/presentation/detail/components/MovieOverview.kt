package com.soe.movieticketapp.presentation.detail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soe.movieticketapp.R
import com.soe.movieticketapp.domain.model.Crew
import com.soe.movieticketapp.domain.model.Genre
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.presentation.common.LabelText
import com.soe.movieticketapp.presentation.common.TitleText
import com.soe.movieticketapp.util.FontSize
import com.soe.movieticketapp.util.Padding
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme


@Composable
fun MovieOverview(
    modifier: Modifier = Modifier,
    movie: Movie,
    crew: List<Crew>
) {

    var showFullText by remember { mutableStateOf(false) }

    // Get the cleaned overview text and handle null case
    val overviewText = movie.overview


    // Determine the display text based on `showFullText` state
    val displayText = if (showFullText || overviewText!!.length <= 200) {
        overviewText
    }else{
        overviewText.take(200) + "..."
    }

    // Build the annotated string with "Read More" or "Read Less"
    val briefOverview = buildAnnotatedString {
        append(displayText)


        if (overviewText!!.length > 200) {
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )
            ){
                append(if (showFullText) stringResource(R.string.read_less) else stringResource(R.string.read_more))
            }
        }


    }





    val director = crew.filter { it.job == "Director" }
    val producer= crew.filter { it.job == "Producer" }
    val writer = crew.filter { it.job == "Writer" }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = Padding.Medium)
                .clickable {
                    if (movie.overview!!.length >= 200) {
                        showFullText = !showFullText
                    }
                },
            letterSpacing = 0.5 .sp,
            text = briefOverview,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = FontSize.Medium,
            color = MaterialTheme.colorScheme.onBackground,

            )

        Spacer(modifier = Modifier.height(Padding.Medium))

        //Director
        Row(
            modifier = Modifier
                .padding(horizontal = Padding.Medium),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {

            TitleText(
                modifier = Modifier.width(100.dp),
                fontSize = FontSize.Medium,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                text = stringResource(R.string.director),
            )

            LabelText(
                modifier = Modifier.fillMaxWidth(),
                fontSize = FontSize.Medium,
                text = if(director.isNotEmpty()) director.joinToString{it.name} else stringResource(R.string.no_data),
            )
        }

        Spacer(modifier = Modifier.height(Padding.Medium))


        //Producer
        Row(
            modifier = Modifier
                .padding(horizontal = Padding.Medium),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {

            TitleText(
                modifier = Modifier.width(100.dp),
                fontSize = FontSize.Medium,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                text = stringResource(R.string.producer),
            )

            LabelText(
                modifier = Modifier.fillMaxWidth(),
                fontSize = FontSize.Medium,
                text = if(director.isNotEmpty()) producer.joinToString{it.name} else stringResource(R.string.no_data),
            )
        }

        Spacer(modifier = Modifier.height(Padding.Medium))

        //Writer
        Row(
            modifier = Modifier
                .padding(horizontal = Padding.Medium),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {

            TitleText(
                modifier = Modifier.width(100.dp),
                fontSize = FontSize.Medium,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                text = stringResource(R.string.writer),
            )

            LabelText(
                modifier = Modifier.fillMaxWidth(),
                fontSize = FontSize.Medium,
                text = if(director.isNotEmpty()) writer.joinToString{it.name} else stringResource(R.string.no_data),
            )
        }
    }

}


@Preview(showBackground = true)
@Composable
private fun MovieOverviewPreview() {
    MovieTicketAppTheme {
        MovieOverview(
            crew = listOf(
            ),
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
