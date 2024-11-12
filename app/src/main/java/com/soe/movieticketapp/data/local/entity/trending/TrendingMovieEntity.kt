package com.soe.movieticketapp.data.local.entity.trending

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class TrendingMovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val posterPath: String,
    val overview: String,
    val releaseDate: String,
    val voteAverage: Double,
    val adult: Boolean,
    val backdropPath: String,
    val firstAirDate: String,
    val genreIds: List<Int>,
    val mediaType: String,
    val name: String,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalName: String,
    val originalTitle: String,
    val popularity: Double,
    val video: Boolean,
    val voteCount: Int,
    val category: String,
    )