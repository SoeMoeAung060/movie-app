package com.soe.movieticketapp.util

fun getSeatName(rowIndex: Int, columnIndex: Int): String {
    val rowLetter = ('A' + rowIndex).toString() // Convert row index to a letter (e.g., 0 -> A, 1 -> B)
    val seatNumber = (columnIndex + 1).toString() // Convert column index to a 1-based number
    return "$rowLetter$seatNumber" // Combine row and seat (e.g., A4, E3)
}

//Explanation
//getSeatName Function:
//
//Converts a rowIndex (e.g., 0 for Row 1) to a letter (A for Row 1, B for Row 2, etc.).
//Converts a columnIndex (e.g., 3 for Seat 4) to a 1-based number (4 for Seat 4).
//Combines the row letter and seat number into a single string (e.g., A4, E3).
