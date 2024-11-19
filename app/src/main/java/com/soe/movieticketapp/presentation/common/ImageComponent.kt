package com.soe.movieticketapp.presentation.common

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import com.soe.movieticketapp.R
import com.soe.movieticketapp.util.Banner
import com.soe.movieticketapp.util.BorderRadius
import com.soe.movieticketapp.util.CardSize
import com.soe.movieticketapp.util.Size
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme


@Composable
fun MovieAsyncImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String? = null,
    context: Context,
    placeholder: Int,
) {
    var imageLoaded by remember { mutableStateOf(false) }

    AsyncImage(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background),
        alignment = Alignment.Center,
        model = ImageRequest.Builder(context)
            .data(imageUrl)
            .placeholder(placeholder)
            .error(placeholder)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        onState = {
            imageLoaded = it is AsyncImagePainter.State.Success
        },
    )
}



@Composable
fun NowPlayingMovieImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    context: Context,
    contextDescription : String,
) {
    MovieAsyncImage(
        modifier = modifier
            .width(CardSize.LargeWidth)
            .height(CardSize.LargeHeight)
            .clip(RoundedCornerShape(BorderRadius.Medium))
            .background(MaterialTheme.colorScheme.background),
        imageUrl = imageUrl,
        contentDescription = contextDescription,
        context = context,
        placeholder = R.drawable.detail_posterdrop_placeholder
    )
}


@Composable
fun LargeCardMovieImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    context: Context,
    contextDescription : String,
) {
    MovieAsyncImage(
        modifier = modifier
            .width(CardSize.MediumWidth)
            .height(CardSize.MediumHeight)
            .clip(RoundedCornerShape(BorderRadius.Medium))
            .background(MaterialTheme.colorScheme.background),
        imageUrl = imageUrl,
        contentDescription = contextDescription,
        context = context,
        placeholder = R.drawable.detail_posterdrop_placeholder
    )
}


@Composable
fun BackDropMovieImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    context: Context,
    contextDescription : String
) {
    MovieAsyncImage(
        modifier = modifier
            .fillMaxWidth()
            .height(Banner.BackDropHeight),
        imageUrl = imageUrl,
        contentDescription = contextDescription,
        context = context,
        placeholder = R.drawable.backdrop_placeholder
    )
}



@Composable
fun SmallCardMovieImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    context: Context,
    contextDescription : String,
    rating : Double?,
    color: Color = if(isSystemInDarkTheme()) Color.Yellow else Color.DarkGray

) {
    Box(){
        MovieAsyncImage(
            modifier = modifier
                .width(CardSize.SmallWidth)
                .height(CardSize.SmallHeight)
                .clip(RoundedCornerShape(BorderRadius.Medium))
                .background(MaterialTheme.colorScheme.background),
            imageUrl = imageUrl,
            contentDescription = contextDescription,
            context = context,
            placeholder = R.drawable.home_posterdrop_placeholder
        )

        RatingText(
            modifier = Modifier
                .clip(RoundedCornerShape(bottomStart = BorderRadius.Small, topEnd = BorderRadius.Small))
                .align(Alignment.TopEnd)
                .background(color),
            rating = rating
        )
    }
}



@Composable
fun ExtraSmallCardMovieImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    context: Context,
    contextDescription : String,
    rating: Double?,
    color: Color = if(isSystemInDarkTheme()) Color.Yellow else Color.DarkGray

) {
    Box(){
        MovieAsyncImage(
            modifier = modifier
                .width(CardSize.ExtraSmallWidth)
                .height(CardSize.ExtraSmallHeight)
                .clip(RoundedCornerShape(BorderRadius.Medium))
                .background(MaterialTheme.colorScheme.background),
            imageUrl = imageUrl,
            contentDescription = contextDescription,
            context = context,
            placeholder = R.drawable.small_poster_placeholder
        )

        RatingText(
            modifier = Modifier
                .clip(RoundedCornerShape(bottomStart = BorderRadius.Small, topEnd = BorderRadius.Small))
                .align(Alignment.TopEnd)
                .background(color),
            rating = rating
        )
    }
}




@Composable
fun SearchExtraSmallCardMovieImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    context: Context,
    contextDescription : String,

) {
    Box(){
        MovieAsyncImage(
            modifier = modifier
                .width(CardSize.SimiExtraSmallWidth)
                .height(CardSize.SimiExtraSmallHeight)
                .clip(RoundedCornerShape(BorderRadius.Medium))
                .background(MaterialTheme.colorScheme.background),
            imageUrl = imageUrl,
            contentDescription = contextDescription,
            context = context,
            placeholder = R.drawable.small_poster_placeholder
        )
    }
}




@Composable
fun AvatarsImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    context: Context,
    contextDescription : String,
) {
    MovieAsyncImage(
        modifier = modifier
            .width(Size.AvatarSizeMedium)
            .height(Size.AvatarSizeMedium)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.background),
        imageUrl = imageUrl,
        contentDescription = contextDescription,
        context = context,
        placeholder = R.drawable.profile_placeholder,
    )
}

@Preview
@Composable
private fun PosterMovieImagePreview() {
    MovieTicketAppTheme {

        SmallCardMovieImage(
            imageUrl = "",
            context = LocalContext.current,
            contextDescription = "Movie Poster",
            rating = 8.0
        )
    }
}