package com.marcomarino.pinweather.network.repositories

import android.util.Log
import com.marcomarino.pinweather.model.WeatherEntries
import com.marcomarino.pinweather.model.WeatherEntry
import com.marcomarino.pinweather.network.API
import com.marcomarino.pinweather.network.NetworkUtility
import com.marcomarino.pinweather.network.RetrofitHelper

class WeatherListRepository(private val token: String) {

    private val weatherAPI: API.WeatherAPI = RetrofitHelper.getInstance().create(API.WeatherAPI::class.java)

    suspend fun makeRequest(baseURL: String): List<WeatherEntry> {
        Log.i("NET-CALL", "Weather list request performed in thread ${Thread.currentThread().name}")
        val url = NetworkUtility.compileValidationUrl(baseURL, token)

        // launching a new coroutine
        val result = weatherAPI.getWeatherList(url)
        Log.i("NET-CALL", result.body().toString())

        return result.body()?.data?.allWeatherEntries ?: listOf()
    }

    suspend fun queryWeathers(baseURL: String, query: String): List<WeatherEntry> {
        val url = NetworkUtility.compileSearchUrl(baseURL, token, query)

        println(url)
        val result = weatherAPI.getWeatherList(url)

        return result.body()?.data?.queriedEntries ?: listOf()
    }

    suspend fun postFavEntry(baseURL: String, id: String) {
        val url = NetworkUtility.compilePlaceOpUrl(baseURL, token, id)

        weatherAPI.postWeatherList(url)
    }

}
