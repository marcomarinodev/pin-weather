package com.marcomarino.pinweather.routers

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.marcomarino.pinweather.model.SessionManager
import com.marcomarino.pinweather.navigation.Routes
import com.marcomarino.pinweather.network.repositories.PostRepository
import com.marcomarino.pinweather.network.repositories.WeatherListRepository
import com.marcomarino.pinweather.viewmodels.AddWeatherViewModel
import com.marcomarino.pinweather.viewmodels.HomeViewModel
import com.marcomarino.pinweather.viewmodels.PostScreenViewModel
import com.marcomarino.pinweather.views.screens.*

@Composable
fun InternalRouter(
    currentScreen: MutableState<String>,
    context: Context,
    internalNavController: NavHostController,
    homeViewModel: HomeViewModel
) {

    val weatherListRepository = WeatherListRepository(SessionManager.token)
    val postRepository = PostRepository(SessionManager.locationsDao)
    val addWeatherViewModel = AddWeatherViewModel(weatherListRepository)
    val postScreenViewModel = PostScreenViewModel(postRepository)

    NavHost(navController = internalNavController, startDestination = Routes.Home.route) {
        composable(route = Routes.Home.route) {
            currentScreen.value = Routes.Home.route
            HomeScreen(context = context, vm = homeViewModel)
        }

        composable(route = Routes.AddWeather.route) {
            currentScreen.value = Routes.AddWeather.route
            AddWeatherScreen(internalNavController, context, vm = addWeatherViewModel)
        }

        composable(route = Routes.Maps.route) {
            currentScreen.value = Routes.Maps.route
            MapScreen(MapScreenViewModel())
        }

        composable(route = Routes.Post.route) {
            currentScreen.value = Routes.Post.route
            PostScreen(vm = postScreenViewModel)
        }
    }
}
