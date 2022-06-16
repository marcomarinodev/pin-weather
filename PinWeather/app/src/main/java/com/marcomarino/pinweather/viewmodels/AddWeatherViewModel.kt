package com.marcomarino.pinweather.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcomarino.pinweather.model.WeatherEntry
import com.marcomarino.pinweather.network.API
import com.marcomarino.pinweather.network.NetworkUtility
import com.marcomarino.pinweather.network.repositories.WeatherListRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class AddWeatherViewModel(private val repo: WeatherListRepository): ViewModel() {

    var errorMessage: String by mutableStateOf("")

    fun searchEntry(state: MutableList<WeatherEntry>, query: String) {
        NetworkUtility.handleCall { viewModelScope.launch {
            try {
                state.clear()
                if (query.isNotEmpty())
                    state.addAll(repo.queryWeathers(
                        API.WeatherAPI.QUERIED_ENTRIES_URL,
                        query
                    ))
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        } }
    }

    fun addFavouriteEntry(id: String, onCompletion: () -> Unit) {
        NetworkUtility.handleCall { viewModelScope.launch {
            try {
                val postResponse = repo.postFavEntry(API.WeatherAPI.ADD_FAV_ENTRY_URL, id)
                onCompletion()
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }}
    }

}