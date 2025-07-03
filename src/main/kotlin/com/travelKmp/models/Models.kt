package com.travelKmp.models

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val name: String,
    val flagIcon: String? = null,
    val touristPlaces: List<TouristPlace> = emptyList()
)

@Serializable
data class TouristPlace(
    val name: String,
    val shortDescription: String,
    val longDescription: String,
    val location: Location,
    val images: List<String> = emptyList()
)

@Serializable
data class Location(
    val lat: Double,
    val long: Double
)