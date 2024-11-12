package com.soe.movieticketapp.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.soe.movieticketapp.util.Padding
import com.soe.movieticketapp.util.Size
import com.soe.movieticketapp.util.ui.theme.MovieIcons
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme

@Composable
fun SearchBox(modifier: Modifier = Modifier) {

    Box(
        modifier = modifier
            .fillMaxWidth()
    ){
        TextField(
            modifier = modifier
                .padding(Padding.Medium)
                .fillMaxWidth()
                .clip(RoundedCornerShape(Size.IconSizeLarge))
                .background(MaterialTheme.colorScheme.primaryContainer),
            value = "",
            onValueChange = {},
            leadingIcon = {
                Icon(
                    painter = painterResource(MovieIcons.search),
                    contentDescription = "Search Icon",
                )
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(MovieIcons.filter),
                    contentDescription = "Filter Icon",
                )
            },
            placeholder = {Text(text = "Search Movies")},
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                focusedTextColor = MaterialTheme.colorScheme.tertiary,
                unfocusedTextColor = MaterialTheme.colorScheme.tertiary,
                cursorColor = MaterialTheme.colorScheme.tertiary,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            )
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun SearchBoxPreview() {
    MovieTicketAppTheme {
        SearchBox()
    }
    
}