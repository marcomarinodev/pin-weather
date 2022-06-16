package com.marcomarino.pinweather.views.screens

import android.content.Context
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavHostController
import com.marcomarino.pinweather.model.WeatherEntry
import com.marcomarino.pinweather.navigation.Routes
import com.marcomarino.pinweather.viewmodels.AddWeatherViewModel
import com.marcomarino.pinweather.views.components.SearchBar
import com.marcomarino.pinweather.views.components.WeatherCardList

@Composable
fun AddWeatherScreen(
    internalNavController: NavHostController,
    context: Context,
    vm: AddWeatherViewModel
) {
    val queryState = remember { mutableStateOf(TextFieldValue()) }
    val weatherListState = remember { mutableStateListOf<WeatherEntry>() }

    Scaffold(
        topBar = {
            SearchBar("Search by city name", state = queryState, onValueChanged = {
                vm.searchEntry(weatherListState, queryState.value.text)
            })
        }
    ) {
        WeatherCardList(entries = weatherListState, context = context) { weatherEntry ->
            vm.addFavouriteEntry(weatherEntry.id, onCompletion = {
                // go back to fav list
                internalNavController.popBackStack()
            })
        }
    }
}