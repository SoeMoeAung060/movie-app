package com.soe.movieticketapp.presentation.otherScreen.castScreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soe.movieticketapp.domain.model.Cast
import com.soe.movieticketapp.domain.model.Crew
import com.soe.movieticketapp.domain.usecase.MovieUseCase
import com.soe.movieticketapp.util.MovieType
import com.soe.movieticketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CastAndCrewViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    private val _castState = mutableStateOf<List<Cast>>(emptyList())
    val castState : State<List<Cast>> = _castState

    private val _crewState = mutableStateOf<List<Crew>>(emptyList())
    val crewState : State<List<Crew>> = _crewState


    fun fetchCastAndCrew(movieId: Int, movieType: MovieType) {

        viewModelScope.launch {
            val result = movieUseCase.getCastAndCrewMovies(movieId, movieType)
            if (result is Resource.Success) {
                _castState.value = result.data?.castResult ?: emptyList()
                _crewState.value = result.data?.crewResult ?: emptyList()
            } else {
                // Handle errors (e.g., log or show a message)
                Log.e("CastAndCrewViewModel", "Failed to fetch cast and crew")
            }
        }
    }

}
