package com.soe.movieticketapp.data.local.entity.popular

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "popular_entity")
data class PopularEntity(
    @PrimaryKey
    val id: Int,
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    val category: String,

)