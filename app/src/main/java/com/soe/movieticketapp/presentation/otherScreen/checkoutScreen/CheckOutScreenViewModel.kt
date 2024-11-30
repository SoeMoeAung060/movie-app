package com.soe.movieticketapp.presentation.otherScreen.checkoutScreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ListenerRegistration
import com.soe.movieticketapp.data.mapper.toDetail
import com.soe.movieticketapp.domain.model.Detail
import com.soe.movieticketapp.domain.usecase.MovieUseCase
import com.soe.movieticketapp.util.MovieType
import com.soe.movieticketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckOutScreenViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
    private val firestore: FirebaseFirestore
): ViewModel(){



    private val _getDetailMovie = mutableStateOf<Detail?>(null)
    val getDetailMovie : State<Detail?> = _getDetailMovie


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