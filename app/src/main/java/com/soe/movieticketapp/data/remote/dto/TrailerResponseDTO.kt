package com.soe.movieticketapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrailerResponseDTO(
    @SerialName("id") val id: Int,
    @SerialName("results") val results: List<Video>
)


@Serializable
data class Video(
    @SerialName("key") val key: String,
    @SerialName("name") val name: String,
    @SerialName("type") val type: String
)
