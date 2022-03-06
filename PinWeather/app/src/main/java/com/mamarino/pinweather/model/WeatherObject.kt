package com.mamarino.pinweather.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherObject (
    val id: Long,
    val cityName: String?,
    val temp: Double?
    )