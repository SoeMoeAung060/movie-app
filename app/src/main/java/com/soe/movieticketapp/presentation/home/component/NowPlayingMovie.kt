package com.soe.movieticketapp.presentation.home.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.soe.movieticketapp.R
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.presentation.common.NowPlayingMovieImage
import com.soe.movieticketapp.presentation.common.RatingBar
import com.soe.movieticketapp.presentation.common.RatingText
import com.soe.movieticketapp.presentation.common.TitleText
import com.soe.movieticketapp.util.BASE_POSTER_IMAGE_URL
import com.soe.movieticketapp.util.Detail
import com.soe.movieticketapp.util.Movie
import com.soe.movieticketapp.util.Padding
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme
import kotlinx.coroutines.flow.flowOf
import kotlin.math.absoluteValue


@Composable
fun NowPlayingMovie(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<Movie>,
    onClick: (Movie) -> Unit
) {

    val statePager = rememberPagerState(
        initialPage = 0,
        pageCount = { movies.itemCount }
    )


    Box(
        modifier = modifier.fillMaxSize(),
    ){
        HorizontalPager(
            state = statePager,
            modifier = modifier,
            contentPadding = PaddingValues(horizontal = 70.dp),
            pageSpacing = Padding.Medium,
            verticalAlignment = Alignment.CenterVertically
        ) { index ->
            val movie = movies[index] ?: return@HorizontalPager

            MovieTicketCard(
                movie = movie,
                index = index,
                pagerState = statePager,
                onClick = onClick
            )
        }
    }
}



@Composable
fun MovieTicketCard(
    modifier: Modifier = Modifier,
    movie: Movie,
    pagerState: PagerState,
    index: Int,
    onClick: (Movie) -> Unit) {

    val duration = movie.runtime?.let { duration ->
        "${duration.div(60)}h ${duration.rem(60)}m"
    } ?: "Duration not available"

    val genresName = movie.genres?.joinToString(", ") { it.name }

    val pagerOffset = (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction

    // Smoothly animate the Z rotation and scale
//    val rotationZ by animateFloatAsState(
//        targetValue = if (pagerOffset != 0f) {
//            lerp(
//                start = -3f, // Rotate left side cards -15 degrees
//                stop = 3f,   // Rotate right side cards 15 degrees
//                fraction = pagerOffset.coerceIn(-1f, 1f)
//            )
//        } else 0f, // Keep the middle card flat
//        animationSpec = tween(durationMillis = 300), // Smooth animation duration
//        label = "RotationZAnimation"
//    )

    val scale by animateFloatAsState(
        targetValue = lerp(
            start = 0.85f, // Scale for side cards
            stop = 1f,     // Scale for the middle card
            fraction = 1f - pagerOffset.absoluteValue.coerceIn(0f, 1f)
        ),
        animationSpec = tween(durationMillis = 100), // Smooth animation duration
        label = "ScaleAnimation"
    )

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(movie) }
            .graphicsLayer {
                // Apply the animated rotation and scale
//                this.rotationZ = rotationZ
                this.scaleX = scale
                this.scaleY = scale
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        NowPlayingMovieImage(
            imageUrl = "$BASE_POSTER_IMAGE_URL${movie.posterPath}",
            context = context,
            contextDescription = movie.title ?: ""

        )

        TitleText(
            modifier = Modifier
                .padding(top = Padding.Small),
            text = movie.title ?: "",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
        )


        Row(
            modifier = Modifier.padding(top = Padding.ExtraSmall)
        ){
            TitleText(
                text = movie.releaseDate ?: "",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal)
            )

            TitleText(
                text = stringResource(R.string.spacing),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal)
            )

            TitleText(
                text = "Language - ${movie.originalLanguage?.uppercase()}",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal)

            )
        }

        Row(
            modifier = Modifier.padding(vertical = Padding.ExtraSmall),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            RatingBar(
                modifier = Modifier,
                starsColor = if (isSystemInDarkTheme()) Color.Yellow else Color.DarkGray,
                rating = (movie.voteAverage!!.div(2)),
            )

            RatingText(
                modifier = Modifier
                    .padding(Padding.Small)
                    .border(
                        BorderStroke(
                            1.dp,
                            if (isSystemInDarkTheme()) Color.Yellow else Color.DarkGray,
                        )
                    )
                    .clip(RoundedCornerShape(Padding.Medium)),
                rating = movie.voteAverage,
                color = if (isSystemInDarkTheme()) Color.Yellow else Color.DarkGray
            )
        }
    }


}

@Preview(showBackground = true)
@Composable
private fun MovieCardPreview() {

    val movie = flowOf(PagingData.from(Movie)).collectAsLazyPagingItems()
    val detail = flowOf(PagingData.from(Detail)).collectAsLazyPagingItems()


    MovieTicketAppTheme {
        NowPlayingMovie(
            movies = movie,
            onClick = {}
        )
    }
}