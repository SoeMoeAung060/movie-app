package com.soe.movieticketapp.presentation.detail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.soe.movieticketapp.data.mapper.toDetail
import com.soe.movieticketapp.data.remote.dto.Results
import com.soe.movieticketapp.data.remote.dto.Video
import com.soe.movieticketapp.domain.model.Cast
import com.soe.movieticketapp.domain.model.Crew
import com.soe.movieticketapp.domain.model.Detail
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.domain.usecase.MovieUseCase
import com.soe.movieticketapp.util.MovieType
import com.soe.movieticketapp.util.Resource
import com.soe.movieticketapp.util.filterTrendingMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
): ViewModel() {

    private val _getCastMovie = mutableStateOf<List<Cast>>(emptyList())
    val getCastMovie : State<List<Cast>> = _getCastMovie

    private val _getCrewMovie = mutableStateOf<List<Crew>>(emptyList())
    val getCrewMovie : State<List<Crew>> = _getCrewMovie

    private val _getWatchProvider = mutableStateOf<Results?>(null)
    val getWatchProvider : State<Results?> = _getWatchProvider

    private  val _getTrailerMovie = mutableStateOf<List<Video>?>(emptyList())
    val getTrailerMovie : State<List<Video>?> = _getTrailerMovie

    private val _getDetailMovie = mutableStateOf<Detail?>(null)
    val getDetailMovie : State<Detail?> = _getDetailMovie


    private val _similarMoviesAndTvSeries = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val similarMoviesAndTvSeries : StateFlow<PagingData<Movie>> = _similarMoviesAndTvSeries


    private val _similar = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val similar : State<Flow<PagingData<Movie>>> = _similar




    fun getSimilarMoviesAndTvSeries(movieId : Int, movieType : MovieType){
        viewModelScope.launch {
            movieUseCase.getSimilarMoviesAndTvSeries(movieId = movieId, movieType = movieType)
                .cachedIn(viewModelScope)
                .collectLatest {
                    val validMovies = it.filterTrendingMovies()
                    _similarMoviesAndTvSeries.value = validMovies
                }
        }
    }



    fun getCastAndCrew(movieId: Int, movieType: MovieType) {
        viewModelScope.launch {
            val result = movieUseCase.getCastAndCrewMovies(movieId, movieType)

            when (result) {
                is Resource.Success -> {
                    result.data?.let { data ->
                        // Load cast
                        data.castResult.let {
                            _getCastMovie.value = it
                            Log.d("GetCast", "Cast data loaded: $it")
                        }

                        // Load crew
                        data.crewResult.let {
                            _getCrewMovie.value = it
                            Log.d("GetCrew", "Crew data loaded: $it")
                        }
                    }
                }
                is Resource.Error -> {
                    Log.d("GetCastAndCrew", "Error loading cast and crew data: ${result.statusMessage}")
                }
                is Resource.Loading -> {
                    Log.d("GetCastAndCrew", "Loading cast and crew data...")
                }
            }
        }
    }


    fun getWatchProvider(movieId: Int, movieType: MovieType) {
        viewModelScope.launch {
            val result = movieUseCase.getWatchProviders(movieId, movieType)
            Log.d("GetWatchProvider", "Watch provider result: $result")
            if (result is Resource.Success && result.data?.results != null) {
                _getWatchProvider.value =result.data.results
                Log.d("GetWatchProvider", "Watch provider data loaded: ${_getWatchProvider.value}")
            }else {
                Log.d("GetWatchProvider", "Error loading watch provider data")
            }
        }
    }


    fun getTrailerMovies(movieId: Int, movieType: MovieType){
        viewModelScope.launch {
            val result = movieUseCase.getTrailersMovie(movieId, movieType)
            if (result is Resource.Success && result.data?.results != null){
                _getTrailerMovie.value = result.data.results
                Log.d("GetTrailerMovies", "Trailer data loaded: ${_getTrailerMovie.value}")
            }

        }
    }


    fun getDetailMovies(movieId: Int, movieType: MovieType){
        viewModelScope.launch {
            val result = movieUseCase.getDetailMovies(movieId, movieType)

            if (result is Resource.Success && result.data != null) {
                _getDetailMovie.value = result.data.toDetail()
                Log.d("GetWatchProvider", "Watch provider data loaded: ${_getDetailMovie.value}")
            }else {
                Log.d("GetWatchProvider", "Error loading watch provider data")
            }
        }
    }

}