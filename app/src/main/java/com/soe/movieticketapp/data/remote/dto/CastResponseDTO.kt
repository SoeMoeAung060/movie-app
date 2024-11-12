package com.soe.movieticketapp.data.remote.dto

import com.soe.movieticketapp.domain.model.Cast
import com.soe.movieticketapp.domain.model.Crew
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastResponseDTO(
    @SerialName("id")
    val id: Int,
    @SerialName("cast")
    val castResult: List<Cast>,
    @SerialName("crew")
    val crewResult: List<Crew>
)
