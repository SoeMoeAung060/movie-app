package com.soe.movieticketapp.presentation.otherScreen.seatScreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ListenerRegistration
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

//    private val _purchaseState = mutableStateOf<PurchaseState>(PurchaseState.Idle)
//    val purchaseState : State<PurchaseState> = _purchaseState

    private val _getDetailMovie = mutableStateOf<Detail?>(null)
    val getDetailMovie : State<Detail?> = _getDetailMovie

    // StateFlow to hold seat statuses
    private val _seats = MutableStateFlow<Map<String, String>>(emptyMap()) // SeatName -> Status
    val seats: StateFlow<Map<String, String>> = _seats

    private var seatsListener: ListenerRegistration? = null

    init {
        initializeAuthentication()
    }

    private fun initializeAuthentication() {
        FirebaseAuth.getInstance().signInAnonymously()
            .addOnSuccessListener { Log.d("Auth", "Signed in anonymously") }
            .addOnFailureListener { e -> Log.e("Auth", "Authentication failed: ${e.message}") }
    }


//    fun purchaseTicket(
//        selectedSeats: Set<Pair<Int, Int>>,
//        selectedTime: String,
//        totalPrice: Int
//    ) {
//        if (selectedSeats.isEmpty()) {
//            _purchaseState.value = PurchaseState.Error("Please select at least one seat")
//            return
//        }
//
//        _purchaseState.value = PurchaseState.Loading
//
//        viewModelScope.launch {
//            try {
//                // Only pass data to the next screen; do not reserve seats here
//                val ticketDetail = TicketDetail(
//                    seats = selectedSeats.map { getSeatName(it.first, it.second) }.joinToString(),
//                    time = selectedTime,
//                    total = totalPrice
//                )
//                _purchaseState.value = PurchaseState.Success(ticketDetail)
//            } catch (e: Exception) {
//                _purchaseState.value = PurchaseState.Error(e.message.toString())
//            }
//        }
//    }

     fun reserveSeatsInFirestore(
        movieId: String,
        showtimeId: String,
        selectedSeats: List<String>,
        userId: String
    ) {
        firestore.runTransaction { transaction ->
            val alreadyReservedSeats = mutableListOf<String>()
            val seatRefs = selectedSeats.map { seatId ->
                firestore.collection("movies")
                    .document(movieId)
                    .collection("showtimes")
                    .document(showtimeId)
                    .collection("seats")
                    .document(seatId)
            }

            // Step 1: Read all seat statuses
            seatRefs.forEach { seatRef ->
                val snapshot = transaction.get(seatRef)
                val currentStatus = snapshot.getString("status") ?: "unknown"

                if (currentStatus == "reserved") {
                    alreadyReservedSeats.add(seatRef.id)
                }
            }

            // Abort the transaction if any seats are already reserved
            if (alreadyReservedSeats.isNotEmpty()) {
                throw FirebaseFirestoreException(
                    "The following seats are already reserved: $alreadyReservedSeats",
                    FirebaseFirestoreException.Code.ABORTED
                )
            }

            // Step 2: Update seat statuses
            seatRefs.forEach { seatRef ->
                transaction.update(seatRef, mapOf(
                    "status" to "reserved",
                    "userId" to userId,
                    "timestamp" to Timestamp.now()
                ))
            }
        }.addOnSuccessListener {
            Log.d("FirestoreTransaction", "Transaction completed successfully.")
        }.addOnFailureListener { e ->
            Log.e("FirestoreTransaction", "Transaction failed: ${e.message}")
        }
    }





//    fun resetPurchaseState(){
//        _purchaseState.value = PurchaseState.Idle
//    }


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
                    Log.d("SeatFlow", "Fetched seat statuses: $seatMap") // Debugging
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

    // Validate seat availability Before Payment
    suspend fun validateSeats(
        movieId: String,
        showtimeId: String,
        selectedSeats: List<String>
    ): Boolean {
        val seatCollection = firestore.collection("movies")
            .document(movieId)
            .collection("showtimes")
            .document(showtimeId)
            .collection("seats")

        val unavailableSeats = mutableListOf<String>()

        for (seat in selectedSeats) {
            val snapshot = seatCollection.document(seat).get().await()
            val currentStatus = snapshot.getString("status") ?: "available"
            if (currentStatus == "reserved") {
                unavailableSeats.add(seat)
            }
        }

        return if (unavailableSeats.isEmpty()) {
            true
        } else {
            Log.e("SeatValidation", "Unavailable seats: $unavailableSeats")
            false
        }
    }



    fun populateSeats(
        movieId: String,
        showtimeId: String,
        rows: Int,
        columns: Int
    ) {
        val seatCollectionRef = firestore.collection("movies")
            .document(movieId)
            .collection("showtimes")
            .document(showtimeId)
            .collection("seats")

        seatCollectionRef.get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.isEmpty) {
                    val batch = firestore.batch()
                    for (row in 0 until rows) {
                        for (col in 0 until columns) {
                            val seatName = "${('A' + row)}${col + 1}"
                            val seatDocRef = seatCollectionRef.document(seatName)
                            batch.set(seatDocRef, mapOf(
                                "status" to "available",
                                "userId" to "",
                                "timestamp" to ""
                            ))
                        }
                    }
                    batch.commit()
                        .addOnSuccessListener { Log.d("Firestore", "Seats populated successfully.") }
                        .addOnFailureListener { e -> Log.e("Firestore", "Error populating seats: ${e.message}") }
                }
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error checking existing seats: ${e.message}")
            }
    }


    override fun onCleared() {
        super.onCleared()
        seatsListener?.remove() // Detach the listener to avoid memory leaks
    }
}