package com.soe.movieticketapp.presentation.otherScreen.searchSrceen.component

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soe.movieticketapp.R
import com.soe.movieticketapp.presentation.common.ExtraIconSize
import com.soe.movieticketapp.presentation.common.SearchExtraSmallCardMovieImage
import com.soe.movieticketapp.presentation.common.SubTitleText
import com.soe.movieticketapp.presentation.common.TitleText
import com.soe.movieticketapp.util.BorderRadius
import com.soe.movieticketapp.util.FontSize
import com.soe.movieticketapp.util.Padding
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme


@Composable
fun ResultsView(
    modifier: Modifier = Modifier,
    imageUrl : String,
    content : Context,
    contextDescription : String,
    title : String,
    overview : String,
    movieType : String,
    onClick : () -> Unit

) {
    val displayText = overview.take(40) +"..."

    Column(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() }
    ){
        Row(
            modifier = modifier
                .padding(Padding.Medium)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ){
            ExtraIconSize(
                modifier = Modifier.padding(end = Padding.Small),
                painter = painterResource(R.drawable.search),
                contentDescription = "search",
                tint = MaterialTheme.colorScheme.onBackground
            )

            SearchExtraSmallCardMovieImage(
                modifier = Modifier.weight(0.2f),
                imageUrl = imageUrl,
                context = content,
                contextDescription = contextDescription
            )

            Column(
                modifier = Modifier
                    .padding(start = Padding.Small)
                    .weight(0.8f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    TitleText(
                        modifier = Modifier.padding(end = Padding.Small),
                        text = title,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(RoundedCornerShape(BorderRadius.Small))
                            .background(if(isSystemInDarkTheme()) Color.Yellow else Color.DarkGray),
                        contentAlignment = Alignment.Center,

                    ){
                        SubTitleText(
                            modifier = Modifier.padding(horizontal = Padding.ExtraSmall),
                            text = movieType,
                            fontSize = FontSize.SemiMedium,
                            color = if (isSystemInDarkTheme()) Color.DarkGray else Color.White
                        )
                    }

                }

                SubTitleText(
                    text = displayText,
                    fontSize = FontSize.SemiMedium
                )
            }

            Box(Modifier
                .height(1.dp)
                .border(1.dp, MaterialTheme.colorScheme.onBackground))
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ResultViewPreview() {
    MovieTicketAppTheme {
        ResultsView(
            imageUrl = "${R.drawable.image01}",
            content = LocalContext.current,
            contextDescription = "null",
            title = "What 8,000 DOGECOIN",
            overview = "In this video, we’ll explore what 8,000 DOGE coins might b",
            movieType = "Movie",
            onClick = {}

        )
    }
    
}