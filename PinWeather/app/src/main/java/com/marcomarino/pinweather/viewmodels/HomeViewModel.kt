package com.marcomarino.pinweather.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcomarino.pinweather.model.WeatherEntry
import com.marcomarino.pinweather.network.API
import com.marcomarino.pinweather.network.repositories.WeatherListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: WeatherListRepository): ViewModel() {

    var errorMessage = mutableStateOf("")
    val weatherList = mutableStateListOf<WeatherEntry>()
    val isRefreshing = mutableStateOf(false)
    val isLoading = mutableStateOf(false)

    fun call() {
        isLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(1000)
                weatherList.clear()
                weatherList.addAll(repo.makeRequest(API.WeatherAPI.ALL_ENTRIES_URL))
            } catch (e: Exception) {
                errorMessage.value = e.message.toString()
            }
            isLoading.value = false
        }
    }

    fun deleteFavEntry(id: String, onCompleted: () -> Unit) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.postFavEntry(API.WeatherAPI.DEL_FAV_ENTRY_URL, id)
                onCompleted()
            } catch (e: Exception) {
                errorMessage.value = e.message.toString()
            }
        }
    }
}