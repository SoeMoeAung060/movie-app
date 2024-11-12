package com.soe.movieticketapp.presentation.otherScreen.movieListScreen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.LazyPagingItems
import com.soe.movieticketapp.R
import com.soe.movieticketapp.domain.model.Genre
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.presentation.common.BodyText
import com.soe.movieticketapp.presentation.common.ExtraSmallCardMovieImage
import com.soe.movieticketapp.presentation.common.HotText
import com.soe.movieticketapp.presentation.common.RatingBar
import com.soe.movieticketapp.presentation.common.TitleListText
import com.soe.movieticketapp.util.Padding
import com.soe.movieticketapp.util.Spacing
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme


fun LazyListScope.movieList(
    modifier: Modifier = Modifier,
    movie: LazyPagingItems<Movie>
) {

    items(movie.itemCount){ index ->
        val movie = movie[index] ?: return@items
        MovieListCard(
            modifier = modifier.padding(vertical = Padding.Small),
            movie = movie
        )
    }
}


@Composable
fun MovieListCard(
    modifier: Modifier = Modifier,
    movie: Movie,
) {

    Box(
        modifier = modifier.fillMaxWidth().padding(horizontal = Padding.Medium)
    ){
        Row(modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)){
            ExtraSmallCardMovieImage(
                modifier = Modifier,
                imageUrl = "https://image.tmdb.org/t/p/w500/${movie.posterPath}",
                context = LocalContext.current,
                contextDescription = movie.title?:"",
                rating = movie.voteAverage
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Padding.Small),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    TitleListText(
                        title = movie.title?:"",

                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        HotText(
                            modifier = Modifier.padding(end = Padding.ExtraSmall),
                            text = stringResource(R.string.hot),

                        )
                        HotText(
                            text = movie.voteCount.toString()
                        )
                    }
                }
                Spacer(modifier = Modifier.height(Spacing.ExtraSmall))
                BodyText(
                    text = movie.overview ?: ""
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    RatingBar(
                        modifier = modifier.padding(end = Padding.Small),
                        rating = (movie.voteAverage?.div(2)),
                    )
                }

            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun MovieListCardPreview() {


    MovieTicketAppTheme {
        MovieListCard(
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