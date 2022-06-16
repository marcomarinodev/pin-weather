package com.marcomarino.pinweather.model

data class WeatherList(
    val data: WeatherEntries
)

data class WeatherEntries(
    val allWeatherEntries: List<WeatherEntry>,
    val queriedEntries: List<WeatherEntry>
)
