package com.soe.movieticketapp.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.soe.movieticketapp.presentation.common.HeaderMovieText


@Composable
fun TopBarHeaderTitle(
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

