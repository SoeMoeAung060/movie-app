package com.soe.movieticketapp.presentation.profile


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.soe.movieticketapp.R
import com.soe.movieticketapp.util.Size
import com.soe.movieticketapp.util.Size.IconSizeLarge
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme

@Composable
fun ProfileHeader(
    modifier: Modifier = Modifier,
) {



    Box(
        modifier = modifier.fillMaxWidth()
    ){
        ListItem(
            modifier = Modifier.fillMaxWidth()
                .padding(top = Size.IconSizeLarge),
            leadingContent = {
                Image(
                    modifier = Modifier
                        .size(IconSizeLarge)
                        .clip(RoundedCornerShape(IconSizeLarge)),
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Localized description",

                    )
            },
            colors = ListItemDefaults.colors(
                containerColor = Color.Transparent
            ),
            headlineContent = { Text(text = "Hello Soe") },
            supportingContent = { Text(text = "Book your favorite movie") },
            trailingContent = {
                Box(
                    modifier = Modifier
                        .size(IconSizeLarge)
                        .clip(RoundedCornerShape(Size.IconSizeLarge))
                        .background(MaterialTheme.colorScheme.tertiaryContainer),
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        modifier = Modifier
                            .size(Size.IconSizeSemiMedium)
                            .background(MaterialTheme.colorScheme.tertiaryContainer),
                        imageVector = Icons.Filled.Notifications,
                        contentDescription = "Localized description"
                    )
                }
            }

        )
    }

}


@Preview(showBackground = true)
@Composable
private fun TopBarPreview() {
    MovieTicketAppTheme {
        ProfileHeader()
    }

}

