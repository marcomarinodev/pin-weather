package com.marcomarino.pinweather.routers

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.marcomarino.pinweather.model.SessionManager
import com.marcomarino.pinweather.navigation.Routes
import com.marcomarino.pinweather.network.repositories.WeatherListRepository
import com.marcomarino.pinweather.viewmodels.HomeViewModel
import com.marcomarino.pinweather.views.screens.MainScreen

@Composable
fun MainRouter(context: Context) {
    val navController = rememberNavController()
    val weatherListRepository = WeatherListRepository(SessionManager.token)
    val homeViewModel = HomeViewModel(weatherListRepository)

    NavHost(navController = navController, startDestination = Routes.Main.route) {

        composable(route = Routes.Main.route) {
            MainScreen(
                navController = navController,
                homeViewModel = homeViewModel,
                context = context
            )
        }

    }
}