package com.soe.movieticketapp.presentation.detail.components

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.soe.movieticketapp.domain.model.Detail
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.presentation.common.LabelText
import com.soe.movieticketapp.presentation.common.RatingBar
import com.soe.movieticketapp.presentation.common.RatingText
import com.soe.movieticketapp.presentation.common.TitleText
import com.soe.movieticketapp.util.Padding
import com.soe.movieticketapp.util.displayYearFromDate


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieDetails(
    modifier: Modifier = Modifier,
    movie: Movie,
    detail: Detail
) {



    val duration = detail.runtime?.let { duration ->
        "${duration.div(60)}h ${duration.rem(60)}m"
    } ?: "Duration not available"


    Log.d("MovieDetails", "MovieDetails Duration: $duration")

    Column(
        modifier = modifier
            .padding(horizontal = Padding.Medium)
    ) {

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
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


            Spacer(modifier = Modifier.padding(horizontal = Padding.ExtraSmall))

            LabelText(
                modifier = Modifier,
                text = "|",
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )

            Spacer(modifier = Modifier.padding(horizontal = Padding.ExtraSmall))


            TitleText(
                modifier = Modifier,
                text = duration,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )

        }

        Row {
            TitleText(
                modifier = Modifier,
                text = movie.releaseDate?.let { displayYearFromDate(it) } ?: "Unknown Year",
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )

            Spacer(modifier = Modifier.padding(horizontal = Padding.ExtraSmall))

            TitleText(
                modifier = Modifier,
                text = "|",
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )

            Spacer(modifier = Modifier.padding(horizontal = Padding.ExtraSmall))

            TitleText(
                modifier = Modifier,
                text = movie.originalLanguage?.uppercase() ?: "Unknown Language",
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )

            Spacer(modifier = Modifier.padding(horizontal = Padding.ExtraSmall))

            TitleText(
                modifier = Modifier,
                text = "|",
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )

            Spacer(modifier = Modifier.padding(horizontal = Padding.ExtraSmall))

            TitleText(
                modifier = Modifier,
                text = movie.mediaType?.uppercase() ?: "Unknown MediaType",
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
        }
    }

}