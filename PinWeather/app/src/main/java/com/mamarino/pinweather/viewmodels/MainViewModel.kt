package com.mamarino.pinweather.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mamarino.pinweather.model.WeatherObject
import kotlin.concurrent.thread

class MainViewModel: ViewModel() {

    var mWeatherObjects = MutableLiveData<ArrayList<WeatherObject>>()
    var mWeatherList = ArrayList<WeatherObject>()
    var mSelectedWeatherObject = MutableLiveData<Long>()

    init {
        mWeatherList = arrayListOf(
            WeatherObject(18, "Pisa", 28.0),
            WeatherObject(19, "Milan", 19.0),
            WeatherObject(20, "Melbourne", 32.0)
        )
        mWeatherObjects.postValue(mWeatherList)
    }

    fun getWeatherObjects(): LiveData<ArrayList<WeatherObject>> {
        return mWeatherObjects
    }

    fun getSelectedWeatherObject() : LiveData<Long> {
        return mSelectedWeatherObject
    }

    fun suggestLocations() {
        thread {
            // Thread.sleep(2000)
            mWeatherList.add(WeatherObject(21, "Sydney", 36.0))
            mWeatherObjects.postValue(mWeatherList)
        }
    }

    fun removeLocationWithId(id: Long) {
        mWeatherList.removeAll { weatherObject -> weatherObject.id == id }
        mWeatherObjects.postValue(mWeatherList)
    }
}