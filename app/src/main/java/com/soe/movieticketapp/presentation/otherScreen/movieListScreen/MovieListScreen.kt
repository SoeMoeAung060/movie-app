package com.soe.movieticketapp.presentation.otherScreen.movieListScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.soe.movieticketapp.presentation.otherScreen.movieListScreen.component.MovieListTopBar
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme

@Composable
fun MovieListScreen(
    modifier: Modifier = Modifier,
    popUp : () -> Unit,
    viewModel: MovieListViewModel = hiltViewModel()
) {

//    val getTrendingMovies = viewModel.getTrendingMovies.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            MovieListTopBar(
                onBackClick = popUp
            )
        }
    ) {
        Box(modifier = modifier.padding(it)) {
           LazyColumn {
//               movieList(
//                   movie = getTrendingMovies
//               )
           }
        }
    }

}

@Preview
@Composable
private fun SeeMoreScreenPreview() {
//    val popular = flowOf(PagingData.from(popularList)).collectAsLazyPagingItems()

    MovieTicketAppTheme {
    }
}
