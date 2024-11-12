package com.soe.movieticketapp.presentation.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.LazyPagingItems
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.presentation.common.LargeCardMovieImage
import com.soe.movieticketapp.util.BASE_POSTER_IMAGE_URL
import com.soe.movieticketapp.util.Padding
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme

@Composable
fun PopularMovie(
    modifier: Modifier = Modifier,
    movie: LazyPagingItems<Movie>
) {


    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier,
        ) {

            LazyRow(
                contentPadding = PaddingValues(
                    horizontal = Padding.Medium,
                ),
                horizontalArrangement = Arrangement.spacedBy(Padding.Medium)
            ) {

                items(count = minOf(movie.itemCount, 20)) { index ->

                    val movie = movie[index]

                    if (movie != null) {
                        LargeCardMovieImage(
                            imageUrl = "$BASE_POSTER_IMAGE_URL/${movie.posterPath}",
                            context = LocalContext.current,
                            contextDescription = "Now Showing"
                        )
                    }

                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
private fun NowShowingPreview() {

//    val movies = flowOf(PagingData.from(popularList)).collectAsLazyPagingItems()

    MovieTicketAppTheme {
//        PopularMovie(
//            movie = movies
//        )
    }

}