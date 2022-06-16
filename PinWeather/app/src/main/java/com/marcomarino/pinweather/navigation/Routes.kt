package com.marcomarino.pinweather.navigation

import com.marcomarino.pinweather.R

sealed class Routes(val route: String, val icon: Int? = null) {
    object Login: Routes("Login")
    object SignUp: Routes("SignUp")
    object Main: Routes("Main")
    object AddWeather: Routes("AddWeather")
    object Home: Routes("Home", R.drawable.ic_home)
    object Maps: Routes("Maps", R.drawable.ic_map)
    object Post: Routes("Post", R.drawable.ic_post)
}