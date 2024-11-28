package com.soe.movieticketapp.presentation.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.soe.movieticketapp.domain.model.Detail
import com.soe.movieticketapp.domain.model.Genre
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.domain.usecase.MovieUseCase
import com.soe.movieticketapp.util.MovieType
import com.soe.movieticketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {


    // current list of genres
    private val _currentCategoryState = mutableStateListOf(Genre(null, "All"))
    val currentCategorySate: SnapshotStateList<Genre> = _currentCategoryState

    // selected genre
    val selectedCategory: MutableState<Genre> = mutableStateOf(Genre(null, "All"))

    // selected movie type (MovieType.MOVIE or MovieType.TV_SHOW)
    val selectedMovieType: MutableState<MovieType> = mutableStateOf(MovieType.MOVIE)


    // Using StateFlow for better data flow handling
    private val _trendingMoviesState = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val trendingMoviesState: State<Flow<PagingData<Movie>>> = _trendingMoviesState

    private val _popularMoviesState = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val popularMoviesState: State<Flow<PagingData<Movie>>> = _popularMoviesState

    private val _topRatedMoviesState = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val topRatedMoviesState: State<Flow<PagingData<Movie>>> = _topRatedMoviesState

    private val _upcomingMoviesState = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val upcomingMoviesState: State<Flow<PagingData<Movie>>> = _upcomingMoviesState

    private val _nowPlayingState = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val nowPlayingState: State<Flow<PagingData<Movie>>> = _nowPlayingState


    init {
        refreshAll()
    }


    fun refreshAll(
        genreId: Int? = selectedCategory.value.id,
        movieType: MovieType = selectedMovieType.value
    ) {
        // Always call getGenreMovies if the genre list is empty or when changing movie types
        if (_currentCategoryState.size == 1 || movieType != selectedMovieType.value) {

            selectedMovieType.value = movieType // Update selected movie type
            getGenreMovies(movieType)
        }
        // Fetch trending movies based on the selected type and genre
        getTrendingMovies(genreId, movieType)
        getPopularMovie(genreId, movieType)
        getTopRateMovie(genreId, movieType)
        getUpcomingMovie(genreId, movieType)
    }


    //Allows users to select a genre, updating the selected genre and refreshing the data.
    fun filterBySelectedGenre(genre: Genre) {
        selectedCategory.value = genre
        refreshAll(genre.id)
        Log.d("Genre:", genre.name)
    }

    //Fetches genres based on the specified movieType (Movie or TV)
    fun getGenreMovies(movieType: MovieType = selectedMovieType.value) {
        viewModelScope.launch {
            val defaultGenre = Genre(null, "All")
            when (val result = movieUseCase.getGenreMovies(movieType = movieType)) {
                is Resource.Success -> {
                    // Clear the existing state if you want to replace it
                    _currentCategoryState.clear()
                    // Add the default "All" genre
                    _currentCategoryState.add(defaultGenre)
                    selectedCategory.value = defaultGenre
                    // Add each genre from the fetched data (result.data?.genres)
                    result.data?.genres?.forEach {
                        // Adds each Genre from the list into _currentCategoryState
                        _currentCategoryState.add(it)
                    }

                    Log.d("HomeViewModel", "Genres loaded: ${_currentCategoryState.size}")
                    _currentCategoryState.forEach { genre ->
                        Log.d("HomeViewModel", "Genre name: ${genre.name}")
                    }
                }

                is Resource.Error -> {
                    Log.e("Error", "Error: Loading Genre")

                }

                else -> {}
            }
        }
    }


    private fun getTrendingMovies(genreId: Int?, movieType: MovieType) {
        viewModelScope.launch {
            _trendingMoviesState.value = if (genreId != null) {
                movieUseCase.getTrendingMovies(movieType).map {
                    it.filter { movie ->
                        movie.genreIds?.contains(genreId) ?: false
                    }
                }.cachedIn(viewModelScope)
            } else {
                movieUseCase.getTrendingMovies(movieType)
                    .cachedIn(viewModelScope)
            }
        }
    }



    init {
        getNowPlayingMovies()
    }

    private fun getNowPlayingMovies() {
        viewModelScope.launch {
            _nowPlayingState.value =
                movieUseCase.getNowPlayingMovies()
                    .cachedIn(viewModelScope)
        }
    }


    private fun getPopularMovie(genreId: Int?, movieType: MovieType) {
        viewModelScope.launch {
            _popularMoviesState.value = if (genreId != null) {
                movieUseCase.getPopularMovies(movieType).map {
                    it.filter { movie ->
                        movie.genreIds?.contains(genreId) ?: false
                    }
                }.cachedIn(viewModelScope)
            } else {
                movieUseCase.getPopularMovies(movieType)
                    .cachedIn(viewModelScope)
            }
        }
    }

    private fun getTopRateMovie(genreId: Int?, movieType: MovieType) {
        viewModelScope.launch {
            _topRatedMoviesState.value = if (genreId != null) {
                movieUseCase.getTopRatedMovies(movieType).map {
                    it.filter { movie ->
                        movie.genreIds?.contains(genreId) ?: false
                    }
                }.cachedIn(viewModelScope)
            } else {
                movieUseCase.getTopRatedMovies(movieType)
                    .cachedIn(viewModelScope)
            }
        }
    }

    private fun getUpcomingMovie(genreId: Int?, movieType: MovieType) {
        viewModelScope.launch {
            _upcomingMoviesState.value = if (genreId != null) {
                movieUseCase.getUpcomingMovies(movieType).map {
                    it.filter { movie ->
                        movie.genreIds?.contains(genreId) ?: false
                    }
                }.cachedIn(viewModelScope)
            } else {
                movieUseCase.getUpcomingMovies(movieType)
                    .cachedIn(viewModelScope)
            }
        }
    }


}
