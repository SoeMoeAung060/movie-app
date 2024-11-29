package com.soe.movieticketapp.presentation.otherScreen.seatScreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.soe.movieticketapp.data.mapper.toDetail
import com.soe.movieticketapp.domain.model.Detail
import com.soe.movieticketapp.domain.usecase.MovieUseCase
import com.soe.movieticketapp.util.MovieType
import com.soe.movieticketapp.util.Resource
import com.soe.movieticketapp.util.getSeatName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@HiltViewModel
class SeatScreenViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
    private val firestore: FirebaseFirestore,
) : ViewModel(){

    private val _purchaseState = mutableStateOf<PurchaseState>(PurchaseState.Idle)
    val purchaseState : State<PurchaseState> = _purchaseState

    private val _getDetailMovie = mutableStateOf<Detail?>(null)
    val getDetailMovie : State<Detail?> = _getDetailMovie

    // StateFlow to hold seat statuses
    private val _seats = MutableStateFlow<Map<String, String>>(emptyMap()) // SeatName -> Status
    val seats: StateFlow<Map<String, String>> = _seats

    fun purchaseTicket(
        selectedSeats : Set<Pair<Int, Int>>,
        selectedTime : String,
        totalPrice : Int,
        movieId : String,
        userId : String
    ){

        Log.d("PurchaseTicket", "Processing selectedSeats: $selectedSeats")
        if(selectedSeats.isEmpty()){
            _purchaseState.value = PurchaseState.Error("Please select at least one seat")
            return
        }

        _purchaseState.value = PurchaseState.Loading

        viewModelScope.launch {

            try {
                Log.d("SelectedSeats", "Seats selected: $selectedSeats")
                val seatToReserve = selectedSeats.map {getSeatName(it.first, it.second)}
                Log.d("MappedSeats", "Mapped seat names: $seatToReserve")
                reserveSeatsInFirestore(
                    movieId = movieId,
                    showtimeId = selectedTime,
                    selectedSeats = seatToReserve,
                    userId = userId,
                )
                val ticketDetail = TicketDetail(
                    seats = seatToReserve.joinToString(), // Convert seat names to a single string
                    time = selectedTime,
                    total = totalPrice
                )
                _purchaseState.value = PurchaseState.Success(ticketDetail)
            }catch (e : Exception){
                _purchaseState.value = PurchaseState.Error(e.message.toString())
            }
        }
    }

    private fun reserveSeatsInFirestore(
        movieId: String,
        showtimeId: String,
        selectedSeats: List<String>,
        userId: String
    ) {
        firestore.runTransaction { transaction ->
            Log.d("FirestoreDebug", "reserveSeatsInFirestore called with: $selectedSeats")
            selectedSeats.forEach { seatId ->
                Log.d("ProcessingSeat", "Processing seat: $seatId") // Log each seat being processed
                try {
                    val seatDocRef = firestore
                        .collection("movies")
                        .document(movieId)
                        .collection("showtimes")
                        .document(showtimeId)
                        .collection("seats")
                        .document(seatId)

                    Log.d("FirestorePath", "Updating seat path: ${seatDocRef.path}")

                    val seatSnapshot = transaction.get(seatDocRef)
                    val currentStatus = seatSnapshot.getString("status")

                    if (currentStatus == "available") {
                        transaction.update(
                            seatDocRef,
                            mapOf(
                                "status" to "reserved",
                                "userId" to userId,
                                "timestamp" to Timestamp.now().toDate().toString()
                            )
                        )
                        Log.d("FirestoreUpdate", "Seat $seatId reserved successfully.")
                    } else if (currentStatus == "reserved") {
                        throw IllegalStateException("Seat $seatId is already reserved.")
                    } else {
                        throw IllegalStateException("Invalid seat status for $seatId: $currentStatus")
                    }
                }catch (e:Exception){
                    Log.e("FirestoreError", "Error processing seat $seatId: ${e.message}")
                }
            }
        }.addOnSuccessListener {
            Log.d("FirestoreTransaction", "Transaction completed successfully.")
        }.addOnFailureListener { exception ->
            Log.e("FirestoreTransaction", "Transaction failed: ${exception.message}")
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



    // Fetch seats from Firestore
    fun fetchSeats(movieId: String, showtimeId: String) {
        firestore.collection("movies")
            .document(movieId)
            .collection("showtimes")
            .document(showtimeId)
            .collection("seats")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("FirestoreError", "Error fetching seats: ${e.message}")
                    return@addSnapshotListener
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    val seatMap = snapshot.documents.associate { doc ->
                        doc.id to (doc.getString("status") ?: "available")
                    }
                    _seats.value = seatMap
                }
            }
    }

    // Update seat status in Firestore
    fun updateSeatStatus(
        movieId: String,
        showtimeId: String,
        seatId: String,
        newStatus: String
    ) {
        firestore.collection("movies")
            .document(movieId)
            .collection("showtimes")
            .document(showtimeId)
            .collection("seats")
            .document(seatId)
            .update("status", newStatus)
            .addOnSuccessListener {
                Log.d("Firestore", "Seat $seatId updated to $newStatus")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error updating seat: ${e.message}")
            }
    }

    // Validate seat availability
    suspend fun validateSeats(movieId: String, showtimeId: String, selectedSeats: List<String>): Boolean {
        val seatCollection = firestore.collection("movies")
            .document(movieId)
            .collection("showtimes")
            .document(showtimeId)
            .collection("seats")

        for (seat in selectedSeats) {
            val snapshot = seatCollection.document(seat).get().await()
            if (snapshot.exists() && snapshot.getString("status") != "available") {
                return false
            }
        }
        return true
    }

    fun populateSeats(movieId: String, showtimeId: String, rows: Int, columns: Int) {
        val batch = firestore.batch()
        for (row in 0 until rows) {
            for (col in 0 until columns) {
                val seatName = "${('A' + row)}${col + 1}" // Generate seat names (e.g., A1, B5)
                val seatDocRef = firestore
                    .collection("movies")
                    .document(movieId)
                    .collection("showtimes")
                    .document(showtimeId)
                    .collection("seats")
                    .document(seatName)

                batch.set(seatDocRef, mapOf(
                    "status" to "available",
                    "userId" to null,
                    "timestamp" to null
                ))
            }
        }

        batch.commit()
            .addOnSuccessListener { Log.d("Firestore", "Seats populated successfully.") }
            .addOnFailureListener { e -> Log.e("Firestore", "Error populating seats: ${e.message}") }
    }


}