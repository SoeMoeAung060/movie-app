@file:Suppress("NAME_SHADOWING")

package com.soe.movieticketapp.presentation.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.presentation.common.RatingBar
import com.soe.movieticketapp.presentation.common.SmallCardMovieImage
import com.soe.movieticketapp.presentation.common.TitleText
import com.soe.movieticketapp.util.BoxSize
import com.soe.movieticketapp.util.Movie
import com.soe.movieticketapp.util.Padding
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun TrendingMovie(
    modifier: Modifier = Modifier,
    movie: LazyPagingItems<Movie>,
    onClick: (Movie) -> Unit
) {

    Column (
        modifier = modifier
            .fillMaxWidth()
            .height(636.dp)
            .padding(horizontal = Padding.Medium),
    ){
        LazyVerticalGrid(
            modifier = Modifier.padding(),
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Center
        ){
            items(count = minOf(movie.itemCount, 9)) { index->
                val movie = movie[index] ?: return@items

                TrendingGridItem(
                    movie = movie,
                    onClick = { onClick(movie) }
                )
            }

        }
    }

}


@Composable
fun TrendingGridItem(
    modifier: Modifier = Modifier,
    movie: Movie,
    onClick: () -> Unit
) {

    Box(
        modifier = modifier
            .width(BoxSize.SmallWidth)
            .height(BoxSize.SmallHeight + 10.dp)
            .padding(vertical = Padding.ExtraSmall)
            .clickable { onClick.invoke() }
    ){

        Column {
            SmallCardMovieImage(
                imageUrl = "https://image.tmdb.org/t/p/w500/${movie.posterPath}",
                contextDescription = movie.title ?: "Treading",
                context = LocalContext.current,
                rating = movie.voteAverage,

            )
            TitleText(
                text = if(movie.title.isNullOrEmpty()) movie.originalName ?: "Unknown Name" else movie.title
            )
            RatingBar(
                modifier = modifier.padding(end = Padding.Small),
                rating = (movie.voteAverage?.div(2)),
            )
        }
    }

}


@Preview(showBackground = true)
@Composable
private fun TrendingMoviePreview() {

    val movie = flowOf(PagingData.from(Movie)).collectAsLazyPagingItems()

    MovieTicketAppTheme {
        TrendingMovie(
            movie = movie,
            onClick = {}
        )
    }

}