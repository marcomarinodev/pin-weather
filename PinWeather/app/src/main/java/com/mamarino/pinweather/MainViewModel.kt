package com.mamarino.pinweather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.concurrent.thread

class MainViewModel: ViewModel() {

    var mName = MutableLiveData<String>()

    init {
        mName.postValue("Starting value")
    }

    fun getName(): LiveData<String> {
        return mName
    }

    fun suggestName() {
        thread {
            Thread.sleep(5000)
            mName.postValue("Modified value")
        }
    }
}