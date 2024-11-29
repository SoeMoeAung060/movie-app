package com.soe.movieticketapp.presentation.otherScreen.seatScreen

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

fun populateSeats(
    movieId: String,       // Movie ID
    showtimeId: String,    // Showtime ID under the movie
    totalRows: Int,        // Total rows in the seating layout
    totalColumns: Int      // Total columns per row
) {
    val firestore = FirebaseFirestore.getInstance()
    val batch = firestore.batch() // Batch to group multiple writes

    for (row in 0 until totalRows) {
        for (column in 0 until totalColumns) {
            val seatName = "${('A' + row)}${column + 1}" // Seat names: A1, B2, etc.
            val seatDocRef = firestore.collection("movies")
                .document(movieId)
                .collection("showtimeId")
                .document(showtimeId)
                .collection("seats")
                .document(seatName)

            // Add default fields for the seat
            batch.set(seatDocRef, mapOf(
                "status" to "available",
                "reservedBy" to null,
                "timestamp" to null
            ))
        }
    }

    // Commit all writes in the batch
    batch.commit()
        .addOnSuccessListener {
            Log.d("FirestorePopulate", "Seats populated successfully for $movieId/$showtimeId")
        }
        .addOnFailureListener { exception ->
            Log.e("FirestorePopulate", "Error populating seats: ${exception.message}")
        }
}
