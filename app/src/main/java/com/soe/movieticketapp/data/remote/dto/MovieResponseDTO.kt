package com.soe.movieticketapp.data.remote.dto

import com.soe.movieticketapp.domain.model.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponseDTO(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<Movie>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)