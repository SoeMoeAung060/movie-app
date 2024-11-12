package com.soe.movieticketapp.presentation.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.soe.movieticketapp.R
import com.soe.movieticketapp.presentation.common.HeaderMovieText
import com.soe.movieticketapp.presentation.common.IconButton
import com.soe.movieticketapp.presentation.common.LabelText
import com.soe.movieticketapp.util.FontSize
import com.soe.movieticketapp.util.Padding
import com.soe.movieticketapp.util.ui.theme.MovieIcons
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme


@Composable
fun HeaderTextLine(
    modifier: Modifier = Modifier,
    headerText: String,
    seeMoreText : String,
    onClick : () -> Unit
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
        Row(
            modifier = Modifier.clickable { onClick() },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LabelText(
                modifier = Modifier,
                fontSize = FontSize.SemiMedium,
                text = seeMoreText,
            )
            IconButton(
                painter = painterResource(MovieIcons.arrowRight),
                contentDescription = stringResource(R.string.see_more),
                tint = MaterialTheme.colorScheme.onBackground,
            )
        }
    }

}


@Preview(showBackground = true)
@Composable
private fun HeaderTextLinePreview() {
    MovieTicketAppTheme {
        HeaderTextLine(
            headerText = "Trending",
            seeMoreText = "See More",
            onClick = {}
        )
    }
    
}