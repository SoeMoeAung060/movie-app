package com.soe.movieticketapp.presentation.home

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.soe.movieticketapp.R
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.presentation.common.HeaderMovieText
import com.soe.movieticketapp.presentation.home.component.Carousel
import com.soe.movieticketapp.presentation.home.component.GenreSelectContent
import com.soe.movieticketapp.presentation.home.component.HeaderTextLine
import com.soe.movieticketapp.presentation.home.component.NowPlayingHeader
import com.soe.movieticketapp.presentation.home.component.NowPlayingMovie
import com.soe.movieticketapp.presentation.home.component.TopBar
import com.soe.movieticketapp.presentation.home.component.TrendingMovie
import com.soe.movieticketapp.util.Padding
import com.soe.movieticketapp.util.Spacing
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToDetailScreen: (Movie) -> Unit,
    navigateToListScreen: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToSearchScreen: () -> Unit,

    ) {



    // Collect trending movies
    val trendingContent = viewModel.trendingMoviesState.value.collectAsLazyPagingItems()
    Log.d("HomeScreen", "trendingMovies itemCount: ${trendingContent.itemCount}")

    val nowPlayingState = viewModel.nowPlayingState.value.collectAsLazyPagingItems()
    val popularState = viewModel.popularMoviesState.value.collectAsLazyPagingItems()
    val topRatedState = viewModel.topRatedMoviesState.value.collectAsLazyPagingItems()
    val upcomingState = viewModel.upcomingMoviesState.value.collectAsLazyPagingItems()


    // Track loading and error states
    val isLoading = trendingContent.loadState.refresh is LoadState.Loading
    val isError = trendingContent.loadState.refresh is LoadState.Error
    val errorMessage = if (isError) {
        (trendingContent.loadState.refresh as LoadState.Error).error.localizedMessage
            ?: "Unknown error"
    } else null


    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopBar(
                navigateToSearchScreen = navigateToSearchScreen,
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    top = paddingValues.calculateTopPadding()
                ),
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (isError) {
                Text(
                    text = errorMessage ?: "An error occurred",
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error
                )
            } else {
                LazyColumn(
                    state = listState,
                    modifier = modifier.fillMaxSize(),
                ) {


                    if (trendingContent.itemCount > 0) {
                        item {

                            Carousel(
                                onClick = { navigateToDetailScreen(trendingContent[0]!!) },
                                movie = trendingContent[0]!!
                            )
                            Spacer(modifier = Modifier.height(Spacing.Large))

                            NowPlayingHeader(
                                headerText = stringResource(R.string.now_playing)
                            )

                            Spacer(Modifier.height(Padding.Large))

                            NowPlayingMovie(
                                movies = nowPlayingState,
                                onClick = navigateToDetailScreen
                            )


                            Spacer(Modifier.height(Padding.Medium))


                        }

                        item {
                            HeaderMovieText(
                                modifier = modifier.padding(horizontal = Padding.Medium),
                                text = stringResource(R.string.movie_category),
                            )
                            Spacer(modifier = Modifier.height(Spacing.Medium))
                        }

                        stickyHeader {
                            GenreSelectContent()
                            Spacer(modifier = Modifier.height(Spacing.Medium))
                        }

                        // Trending Movies
                        item {
                            Spacer(modifier = Modifier.height(Spacing.Small))

                            HeaderTextLine(
                                modifier = modifier,
                                headerText = stringResource(R.string.trending),
                                seeMoreText = stringResource(R.string.see_more),
                                onClick = { navigateToListScreen() }
                            )

                            Spacer(modifier = Modifier.height(Spacing.Small))

                            TrendingMovie(
                                modifier = modifier,
                                movie = trendingContent,
                                onClick = { navigateToDetailScreen(it) }
                            )
                        }

                        // Popular Movies
                        item {
                            Spacer(modifier = Modifier.height(Spacing.Small))

                            HeaderTextLine(
                                modifier = modifier,
                                headerText = stringResource(R.string.popular),
                                seeMoreText = stringResource(R.string.see_more),
                                onClick = { navigateToListScreen() }
                            )

                            Spacer(modifier = Modifier.height(Spacing.Small))

                            TrendingMovie(
                                modifier = modifier,
                                movie = popularState,
                                onClick = { navigateToDetailScreen(it) }
                            )
                        }

                        // Top Rate Movies
                        item {
                            Spacer(modifier = Modifier.height(Spacing.Small))

                            HeaderTextLine(
                                modifier = modifier,
                                headerText = stringResource(R.string.top_rate),
                                seeMoreText = stringResource(R.string.see_more),
                                onClick = { navigateToListScreen() }
                            )

                            Spacer(modifier = Modifier.height(Spacing.Small))

                            TrendingMovie(
                                modifier = modifier,
                                movie = topRatedState,
                                onClick = { navigateToDetailScreen(it) }
                            )
                        }

                        // Upcoming Movies
                        item {
                            Spacer(modifier = Modifier.height(Spacing.Small))

                            HeaderTextLine(
                                modifier = modifier,
                                headerText = stringResource(R.string.upcoming),
                                seeMoreText = stringResource(R.string.see_more),
                                onClick = { navigateToListScreen() }
                            )

                            Spacer(modifier = Modifier.height(Spacing.Small))

                            TrendingMovie(
                                modifier = modifier,
                                movie = upcomingState,
                                onClick = { navigateToDetailScreen(it) }
                            )
                        }


                    }else{
                        Log.d("HomeScreen", "No TV show found")
                    }
                }
            }

        }

    }
}


@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun HomeScreenPreview() {


    MovieTicketAppTheme {
        HomeScreen(
            navigateToDetailScreen = {},
            navigateToListScreen = {},
            navigateToSearchScreen = {}

            )
    }

}

