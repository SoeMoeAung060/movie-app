
package com.soe.movieticketapp.presentation.detail.components

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
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.soe.movieticketapp.R
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.presentation.common.BodyText
import com.soe.movieticketapp.presentation.common.RatingBar
import com.soe.movieticketapp.presentation.common.SmallCardMovieImage
import com.soe.movieticketapp.presentation.common.TitleText
import com.soe.movieticketapp.util.BoxSize
import com.soe.movieticketapp.util.FontSize
import com.soe.movieticketapp.util.Movie
import com.soe.movieticketapp.util.Padding
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun SimilarMovieContent(
    modifier: Modifier = Modifier,
    movie: LazyPagingItems<Movie>,
    onClick: (Movie) -> Unit,
) {


    val height = if (movie.itemCount > 0) 636.dp else 0.dp


    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        if (movie.itemCount > 0){
            TitleText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Padding.Medium),
                fontSize = FontSize.Large,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                text = stringResource(R.string.similar_movies),
            )

            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height)
                    .padding(horizontal = Padding.Medium),
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.Center
            ){
                items(count = minOf(movie.itemCount, 9)){index ->
                    val item = movie[index]
                    if(item != null) {
                        SimilarGridItem(
                            movie = item,
                            onClick = {
                                onClick.invoke(item)
                            }
                        )
                    }
                }

            }
        }





    }

}




@Composable
fun SimilarGridItem(
    modifier: Modifier = Modifier,
    movie: Movie,
    onClick: () -> Unit
) {

    Box(
        modifier = modifier
            .width(BoxSize.SmallWidth)
            .height(BoxSize.SmallHeight + 10.dp)
            .padding( vertical= Padding.ExtraSmall)
            .clickable { onClick.invoke() }
    ){

        Column {
            SmallCardMovieImage(
                imageUrl = "https://image.tmdb.org/t/p/w500/${movie.posterPath}",
                contextDescription = movie.title ?: "Similar",
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


@Preview
@Composable
private fun SimilarMoviePreview() {

    val movie = flowOf(PagingData.from(Movie)).collectAsLazyPagingItems()

    MovieTicketAppTheme {
        SimilarMovieContent(
            movie = movie,
            onClick = {}
        )
    }
    
}