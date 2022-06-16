package com.marcomarino.pinweather.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcomarino.pinweather.model.WeatherEntry
import com.marcomarino.pinweather.network.API
import com.marcomarino.pinweather.network.NetworkUtility
import com.marcomarino.pinweather.network.repositories.WeatherListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel(private val repo: WeatherListRepository): ViewModel() {

    private val _weatherList = mutableStateListOf<WeatherEntry>()
    private val _isRefreshing = MutableStateFlow<Boolean>(false)
    var errorMessage: String by mutableStateOf("")
    val weatherList: List<WeatherEntry>
        get() = _weatherList

    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    fun call() {
        NetworkUtility.handleCall { viewModelScope.launch {
            try {
                _weatherList.clear()
                _weatherList.addAll(repo.makeRequest(API.WeatherAPI.ALL_ENTRIES_URL))
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        } }
    }

    fun deleteFavEntry(id: String, onCompleted: () -> Unit) {
        NetworkUtility.handleCall { viewModelScope.launch {
            try {
                repo.postFavEntry(API.WeatherAPI.DEL_FAV_ENTRY_URL, id)
                onCompleted()
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        } }
    }
}