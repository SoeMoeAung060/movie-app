package com.soe.movieticketapp.util

import java.text.DecimalFormat


fun formatRating(rating: Double?) : String {
    return String.format("%.1f", rating ?: 0.0)
}
