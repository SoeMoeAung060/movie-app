package com.soe.movieticketapp.presentation.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.soe.movieticketapp.presentation.common.HeaderMovieText
import com.soe.movieticketapp.presentation.common.LabelText
import com.soe.movieticketapp.util.FontSize
import com.soe.movieticketapp.util.Padding
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme


@Composable
fun NowPlayingHeader(
    modifier: Modifier = Modifier,
    headerText: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Padding.Medium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        HeaderMovieText(
            text = headerText
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun HeaderTextLinePreview() {
    MovieTicketAppTheme {
        NowPlayingHeader(
            headerText = "Trending",
        )
    }

}