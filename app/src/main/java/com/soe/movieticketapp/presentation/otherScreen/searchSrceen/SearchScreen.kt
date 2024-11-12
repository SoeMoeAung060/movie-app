package com.soe.movieticketapp.presentation.otherScreen.searchSrceen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.presentation.otherScreen.searchSrceen.component.MovieSearchBar
import com.soe.movieticketapp.presentation.otherScreen.searchSrceen.component.ResultsView
import com.soe.movieticketapp.presentation.otherScreen.searchSrceen.component.SearchHistoryView
import com.soe.movieticketapp.util.BASE_POSTER_IMAGE_URL

@Composable
fun SearchScreen(
    navigateToDetailScreen: (Movie) -> Unit,
    modifier: Modifier = Modifier,
    popUp: () -> Unit,
    searchViewModel: SearchViewModel = hiltViewModel(),
) {
    val searchResult = searchViewModel.multiSearchState.collectAsLazyPagingItems()
    val searchHistory = searchViewModel.searchHistory
    val searchQuery by searchViewModel.searchQuery // Observe search query

    Scaffold(
        topBar = {
            MovieSearchBar(
                popUp = popUp,
                onSearch = { searchViewModel.getSearchMovies() },
            )
        }
    ) { padding ->
        Column(modifier = modifier.padding(padding)) {

            // Debug Log to check the content of searchHistory
            Log.d("SearchScreen", "Search history: $searchHistory")
            Log.d("SearchScreen", "Search query: $searchQuery")

            // Display Search History if it exists
            if (searchHistory.isNotEmpty() && searchQuery.isEmpty()) {
                SearchHistoryView(
                    searchHistory = searchHistory,
                    onClearHistory = { searchViewModel.clearSearchHistory() },
                    onHistoryItemClick = { query ->
                        searchViewModel.searchQuery.value = query
                        searchViewModel.getSearchMovies()
                    }
                )
            }

            // Show search results if there is an active search query
            if (searchViewModel.searchQuery.value.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    when (searchResult.loadState.refresh) {
                        is LoadState.NotLoading -> {
                            items(count = searchResult.itemCount) { index ->
                                val movies = searchResult[index]
                                val focus = LocalFocusManager.current

                                ResultsView(
                                    modifier = modifier,
                                    imageUrl = movies?.posterPath?.let { "${BASE_POSTER_IMAGE_URL}$it" } ?: "",
                                    content = LocalContext.current,
                                    contextDescription = movies?.title ?: "",
                                    title = movies?.title ?: "",
                                    overview = movies?.overview ?: "",
                                    movieType = movies?.mediaType ?: "",
                                    onClick = {
                                        val navMovie = Movie(
                                            adult = movies?.adult ?: false,
                                            backdropPath = movies?.backdropPath ?: "",
                                            posterPath = movies?.posterPath ?: "",
                                            genreIds = movies?.genreIds ?: emptyList(),
                                            genres = movies?.genres ?: emptyList(),
                                            mediaType = movies?.mediaType ?: "",
                                            firstAirDate = movies?.firstAirDate ?: "",
                                            id = movies?.id ?: 0,
                                            imdbId = movies?.imdbId ?: "",
                                            overview = movies?.overview ?: "",
                                            originalName = movies?.originalName ?: "",
                                            originalLanguage = movies?.originalLanguage ?: "",
                                            popularity = movies?.popularity ?: 0F.toDouble(),
                                            releaseDate = movies?.releaseDate ?: "",
                                            runtime = movies?.runtime ?: 0,
                                            title = movies?.title ?: "",
                                            voteCount = movies?.voteCount ?: 0,
                                            voteAverage = movies?.voteAverage ?: 0F.toDouble(),
                                            video = movies?.video ?: false
                                        )
                                        focus.clearFocus()
                                        navigateToDetailScreen(navMovie)
                                    }
                                )
                            }
                        }
                        is LoadState.Loading -> {
                            // Display loading indicator if needed
                        }
                        is LoadState.Error -> {
                            // Handle error if needed
                        }
                        else -> Unit
                    }
                }
            }
        }
    }
}
