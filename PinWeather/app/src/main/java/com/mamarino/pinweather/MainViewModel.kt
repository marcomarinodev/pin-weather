package com.mamarino.pinweather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.concurrent.thread

class MainViewModel: ViewModel() {

    var mWeatherObjects = MutableLiveData<ArrayList<WeatherObject>>()

    init {
        mWeatherObjects.postValue(arrayListOf(
            WeatherObject("Pisa", 28.0),
            WeatherObject("Milan", 19.0),
            WeatherObject("Melbourne", 32.0)
        ))
    }

    fun getWeatherObjects(): LiveData<ArrayList<WeatherObject>> {
        return mWeatherObjects
    }

    fun suggestLocations() {
        thread {
            Thread.sleep(5000)
            mWeatherObjects.postValue(arrayListOf(
                WeatherObject("Sydney", 12.0),
                WeatherObject("Paris", 44.0),
                WeatherObject("London", 99.0)
            ))
        }
    }
}