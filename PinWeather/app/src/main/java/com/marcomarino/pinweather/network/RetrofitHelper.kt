package com.marcomarino.pinweather.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// an object is an anonymous class
object RetrofitHelper {

    const val baseUrl = "http://10.0.2.2:8000/"

    fun getInstance(): Retrofit {
        // We need to add converter factory to convert JSON object
        // to kotlin object
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}