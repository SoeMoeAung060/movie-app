package com.soe.movieticketapp.presentation.home.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.soe.movieticketapp.presentation.common.TitleText
import com.soe.movieticketapp.util.GENRES
import com.soe.movieticketapp.util.Padding
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme

@Composable
fun TagScrollBar(
    modifier: Modifier = Modifier,
) {

    LazyRow(
        modifier = modifier.fillMaxWidth().padding(vertical = Padding.Small)
    ) {
        items(count = GENRES.size){ genres ->
            val genre = GENRES[genres]
            TitleText(
                text = genre,
                modifier = Modifier.padding(horizontal = Padding.Medium),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TagScrollBarPreview() {

    MovieTicketAppTheme {
        TagScrollBar()

    }

}