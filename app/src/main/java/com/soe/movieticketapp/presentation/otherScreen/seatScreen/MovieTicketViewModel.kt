package com.soe.movieticketapp.presentation.otherScreen.seatScreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.soe.movieticketapp.data.mapper.toDetail
import com.soe.movieticketapp.domain.model.Detail
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.domain.usecase.MovieUseCase
import com.soe.movieticketapp.util.MovieType
import com.soe.movieticketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieTicketViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    private val _nowPlayingState = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val nowPlayingState: State<Flow<PagingData<Movie>>> = _nowPlayingState

    private val _getDetailMovie = mutableStateOf<Detail?>(null)
    val getDetailMovie : State<Detail?> = _getDetailMovie


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

    fun getDetailMovies(movieId: Int, movieType: MovieType){
        viewModelScope.launch {
            val result = movieUseCase.getDetailMovies(movieId, movieType)

            if (result is Resource.Success && result.data != null) {
                _getDetailMovie.value = result.data.toDetail()
                Log.d("GetDetailMovies", "Watch provider data loaded: ${_getDetailMovie.value}")
            }else {
                Log.d("GetDetailMovies", "Error loading watch provider data")
            }
        }
    }
}