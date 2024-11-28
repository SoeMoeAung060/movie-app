package com.soe.movieticketapp.presentation.detail.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.soe.movieticketapp.R
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.presentation.common.BackDropMovieImage
import com.soe.movieticketapp.presentation.common.BuyTicketButton
import com.soe.movieticketapp.presentation.common.IconButton
import com.soe.movieticketapp.presentation.common.LargeCardMovieImage
import com.soe.movieticketapp.util.BASE_BACKDROP_IMAGE_URL
import com.soe.movieticketapp.util.BASE_POSTER_IMAGE_URL
import com.soe.movieticketapp.util.Padding
import com.soe.movieticketapp.util.Size
import com.soe.movieticketapp.util.ui.theme.MovieIcons


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieBackdropAndPoster(
    modifier: Modifier = Modifier,
    movie: Movie,
    onClickTrailer:(Int?)->Unit,
    onClickBuyTicket: (Movie) -> Unit
) {
    Box(
        modifier = modifier
    ) {
        BackDropMovieImage(
            modifier = Modifier.fillMaxWidth(),
            imageUrl = "$BASE_BACKDROP_IMAGE_URL${movie.backdropPath}",
            context = LocalContext.current,
            contextDescription = movie.title ?: "Unknown Title"
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Transparent, // Bottom is fully transparent
                            MaterialTheme.colorScheme.background.copy(1f), // // Middle has semi-transparency
                            MaterialTheme.colorScheme.background // Top is fully opaque, adjust as needed
                        ),
                        startY = 0f, // Start the gradient from the top
                        endY = Float.POSITIVE_INFINITY // Extend the gradient to cover the full height
                    )
                )
        )

        Box(
            modifier = modifier
                .padding(start = Padding.Medium)
                .align(Alignment.BottomStart)
                .clickable { onClickTrailer(movie.id) },
        ) {
            LargeCardMovieImage(
                imageUrl = "$BASE_POSTER_IMAGE_URL${movie.posterPath}",
                contextDescription = stringResource(R.string.poster),
                context = LocalContext.current
            )

            IconButton(
                modifier = Modifier
                    .size(Size.IconSizeLarge)
                    .align(Alignment.Center),
                painter = painterResource(MovieIcons.playIcon),
                contentDescription = stringResource(R.string.play_icon),
                tint = MaterialTheme.colorScheme.onBackground.copy(0.6f),
            )
        }


        Box(
            modifier = modifier
                .padding(end = Padding.Medium)
                .align(Alignment.BottomEnd),
        ){
            BuyTicketButton(
                onClick = onClickBuyTicket,
                text = "Buy Ticket",
                movie = movie
            )
        }



    }
}

