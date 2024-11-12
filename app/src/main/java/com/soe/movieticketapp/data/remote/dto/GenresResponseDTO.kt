package com.soe.movieticketapp.data.remote.dto

import com.soe.movieticketapp.domain.model.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenresResponseDTO(
    @SerialName("genres") val genres: List<Genre>
)