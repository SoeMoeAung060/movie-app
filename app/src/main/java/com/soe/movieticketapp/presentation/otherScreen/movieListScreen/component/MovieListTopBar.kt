package com.soe.movieticketapp.presentation.otherScreen.movieListScreen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.soe.movieticketapp.R
import com.soe.movieticketapp.util.Padding
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListTopBar(
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                textAlign = TextAlign.Center,
                text = stringResource(R.string.movie_list)
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "back"
                )
            }
        },
        actions = {
            Row(
                modifier = Modifier.padding(end = Padding.Medium),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Movie",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Icon(
                    painter = painterResource(id = R.drawable.arrow_down),
                    contentDescription = "arrow down",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
            titleContentColor = MaterialTheme.colorScheme.onBackground
        )

    )
}


@Preview (showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MovieListTopBarPreview() {
    MovieTicketAppTheme {
        MovieListTopBar(
            onBackClick = {}
        )
    }

}