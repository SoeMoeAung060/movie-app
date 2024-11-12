package com.soe.movieticketapp.presentation.common

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.soe.movieticketapp.R
import com.soe.movieticketapp.util.Size
import com.soe.movieticketapp.util.ui.theme.MovieIcons
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme


@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Double?,
    stars: Int = 5,
    size : Dp = Size.IconSizeExtraSmall,
    starsColor: Color = if(isSystemInDarkTheme()) Color.Yellow else Color.DarkGray,
) {
    // Guard against null rating
    val safeRating = rating ?: 0.0

    // Calculate the number of full stars and check for a half star
    val fullStars = safeRating.toInt() // Number of full stars
    val hasHalfStar = (safeRating - fullStars) >= 0.5 // Check if there's a half star

    Row(modifier = modifier) {
        // Display full stars
        for (index in 1..fullStars) {
            Icon(
                painter = painterResource(MovieIcons.starFull),
                modifier = Modifier.size(size),
                contentDescription = stringResource(R.string.rating_bar),
                tint = starsColor
            )
        }

        // Display half star if needed
        if (hasHalfStar) {
            Icon(
                painter = painterResource(MovieIcons.starHalf),
                modifier = Modifier.size(size),
                contentDescription = stringResource(R.string.rating_bar),
                tint = starsColor
            )
        }

        // Display empty stars for the remaining positions
        val emptyStars = stars - fullStars - if (hasHalfStar) 1 else 0
        for (index in 1..emptyStars) {
            Icon(
                painter = painterResource(MovieIcons.strOutline),
                modifier = Modifier.size(size),
                contentDescription = stringResource(R.string.rating_bar),
                tint = starsColor
            )
        }
    }
}



@Preview
@Composable
private fun RatingBarPreview() {
    MovieTicketAppTheme {
        RatingBar(
            rating = 0.0)
    }

}