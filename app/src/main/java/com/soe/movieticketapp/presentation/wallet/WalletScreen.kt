package com.soe.movieticketapp.presentation.wallet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.soe.movieticketapp.R
import com.soe.movieticketapp.util.TopBarHeaderTitle
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletScreen(
    modifier: Modifier = Modifier,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = modifier,
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.background,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),
                title = {
                    TopBarHeaderTitle(
                        modifier = modifier,
                        headerText = stringResource(R.string.wallet),
                    )
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {



        }
    }
}


@Preview
@Composable
private fun MovieTicketScreenPreview() {
    MovieTicketAppTheme {
        WalletScreen(
        )
    }

}