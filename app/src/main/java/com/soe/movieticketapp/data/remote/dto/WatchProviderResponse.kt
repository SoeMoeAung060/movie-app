package com.soe.movieticketapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WatchProviderResponse(
    @SerialName("id") val id: Int,
    @SerialName("results") val results: Results
)

@Serializable
data class Results(
    @SerialName("US") val us: US? = null // Keep it nullable in case it's missing
)

@Serializable
data class US(
    @SerialName("link") val link: String? = null,
    @SerialName("buy") val buy: List<Buy> = emptyList(), // Default to empty list
    @SerialName("rent") val rent: List<Rent> = emptyList(),
    @SerialName("flatrate") val flatRate: List<Flatrate> = emptyList(),
)

@Serializable
data class Buy(
    @SerialName("display_priority") val displayPriority: Int,
    @SerialName("logo_path") val logoPath: String,
    @SerialName("provider_id") val providerId: Int,
    @SerialName("provider_name") val providerName: String
)

@Serializable
data class Flatrate(
    @SerialName("display_priority") val displayPriority: Int,
    @SerialName("logo_path") val logoPath: String,
    @SerialName("provider_id") val providerId: Int,
    @SerialName("provider_name") val providerName: String
)

@Serializable
data class Rent(
    @SerialName("display_priority") val displayPriority: Int,
    @SerialName("logo_path") val logoPath: String,
    @SerialName("provider_id") val providerId: Int,
    @SerialName("provider_name") val providerName: String
)