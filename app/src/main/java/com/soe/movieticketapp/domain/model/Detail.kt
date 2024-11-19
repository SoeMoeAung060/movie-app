package com.soe.movieticketapp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Detail(
    @SerialName("budget")
    val budget: Int,
    @SerialName("homepage")
    val homepage: String,
    @SerialName("id")
    val id: Int,
    @SerialName("imdb_id")
    val imdbId: String,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_title")
    val originalTitle: String,
    @SerialName("overview")
    val overview: String,
    @SerialName("popularity")
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("revenue")
    val revenue: Int,
    @SerialName("runtime")
    val runtime: Int? = null,
    @SerialName("status")
    val status: String,
    @SerialName("genres")
    val genres: List<Genre>,
    @SerialName("tagline")
    val tagline: String,
    @SerialName("title")
    val title: String,
    @SerialName("video")
    val video: Boolean,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int

)