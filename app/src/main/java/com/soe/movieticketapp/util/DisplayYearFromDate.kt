package com.soe.movieticketapp.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun displayYearFromDate(date : String) : String{
    return try {
        val parseDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        parseDate.year.toString()
    }catch (e : Exception){
        "Invalid Date"
    }

}

