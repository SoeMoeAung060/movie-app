package com.soe.movieticketapp.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter

@ProvidedTypeConverter
class MovieTypeConverter {

    @TypeConverter
    fun fromGenreIds(genreIds: List<Int>): String {
        return genreIds.joinToString(",")
    }

    @TypeConverter
    fun toGenreIds(genreIdsString: String): List<Int> {
        return genreIdsString.split(",").map { it.toInt() }
    }


    @TypeConverter
    fun fromOriginCountry(originCountry: List<String>): String {
        return originCountry.joinToString(",")
    }

    @TypeConverter
    fun toOriginCountry(originCountryString: String): List<String> {
        return originCountryString.split(",").map { it.trim() }
    }

}