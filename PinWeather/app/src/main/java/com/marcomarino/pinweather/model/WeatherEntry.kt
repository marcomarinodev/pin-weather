package com.marcomarino.pinweather.model

// I am using different model just for business logic
// so that I am able to not consider nullchecks
data class WeatherEntry(
    val id: String,
    val city: String,
    val time: String,
    val status: String,
    val temp: Double,
    val max: Double,
    val min: Double
);