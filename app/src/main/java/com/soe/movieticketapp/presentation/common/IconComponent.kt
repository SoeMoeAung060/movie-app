package com.soe.movieticketapp.presentation.common

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import com.soe.movieticketapp.util.Size.IconSizeExtraSmall
import com.soe.movieticketapp.util.Size.IconSizeSemiMedium
import com.soe.movieticketapp.util.Size.IconSizeSmall

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    painter : Painter,
    tint : Color,
    contentDescription : String? = null,
) {

    Icon(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint,
    )

}


@Composable
fun ExtraIconSize(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String? = null,
    tint: Color
) {

    Icon(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier.size(IconSizeExtraSmall),
        tint = tint,
    )
}

