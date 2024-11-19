package com.soe.movieticketapp.presentation.otherScreen.seatScreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soe.movieticketapp.data.mapper.toDetail
import com.soe.movieticketapp.domain.model.Detail
import com.soe.movieticketapp.domain.usecase.MovieUseCase
import com.soe.movieticketapp.util.MovieType
import com.soe.movieticketapp.util.Resource
import com.soe.movieticketapp.util.getSeatName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SeatScreenViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel(){


    private val _purchaseState = mutableStateOf<PurchaseState>(PurchaseState.Idle)
    val purchaseState : State<PurchaseState> = _purchaseState

    private val _getDetailMovie = mutableStateOf<Detail?>(null)
    val getDetailMovie : State<Detail?> = _getDetailMovie


    fun purchaseTicket(
        selectedSeats : Set<Pair<Int, Int>>,
        selectedTime : String,
        totalPrice : Int
    ){

        if(selectedSeats.isEmpty()){
            _purchaseState.value = PurchaseState.Error("Please select at least one seat")
            return
        }

        _purchaseState.value = PurchaseState.Loading

        viewModelScope.launch {

            delay(2000L)
            val success = true


            if(success){
                val ticketDetail = TicketDetail(
                    seats = selectedSeats.joinToString { getSeatName(it.first, it.second) }, // Convert indices to seat name
                    time = selectedTime,
                    total = totalPrice
                )

                _purchaseState.value = PurchaseState.Success(ticketDetail)
            }else{
                _purchaseState.value = PurchaseState.Error("Something went wrong")
            }
        }

    }

    fun resetPurchaseState(){
        _purchaseState.value = PurchaseState.Idle
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