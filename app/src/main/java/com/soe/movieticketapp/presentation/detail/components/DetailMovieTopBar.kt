package com.soe.movieticketapp.presentation.detail.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.soe.movieticketapp.R
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMovieTopBar(
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                textAlign = TextAlign.Center,
                text = stringResource(R.string.detail_screen)
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
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
            titleContentColor = MaterialTheme.colorScheme.onBackground
        )

    )
}


@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MovieListTopBarPreview() {
    MovieTicketAppTheme {
        DetailMovieTopBar(
            onBackClick = {}
        )
    }

}