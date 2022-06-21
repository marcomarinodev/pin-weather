package com.marcomarino.pinweather.views.screens

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.marcomarino.pinweather.R
import com.marcomarino.pinweather.model.SessionManager
import com.marcomarino.pinweather.navigation.Routes
import com.marcomarino.pinweather.routers.InternalRouter
import com.marcomarino.pinweather.viewmodels.HomeViewModel
import com.marcomarino.pinweather.views.components.BottomNavigationBar
import com.marcomarino.pinweather.views.components.TopBar

@Composable
fun MainScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    context: Context
) {

    val internalNavController = rememberNavController()
    val currentInternalScreen = remember { mutableStateOf("") }

    SwipeRefresh (
        state = rememberSwipeRefreshState(isRefreshing = homeViewModel.isRefreshing.value),
        onRefresh = { homeViewModel.call() }
    ) {

        Scaffold(
            topBar = {
                TopBar(
                    navController = navController,
                    title = "PINWeather",
                    showBackIcon = true,
                    showLogo = true
                )
            },
            floatingActionButton = {
                if (currentInternalScreen.value == Routes.Home.route) {
                    FloatingActionButton(onClick = {
                        internalNavController.navigate(Routes.AddWeather.route)
                    },
                        backgroundColor = colorResource(id = R.color.background),
                        contentColor = colorResource(id = R.color.secondary)
                    ) {
                        Icon(imageVector = Icons.Default.Add, "")
                    }
                }
            },
            bottomBar = { BottomNavigationBar(navController = internalNavController) }
        ) { innerPadding ->
            SessionManager.bottomNavigationHeight = innerPadding.calculateBottomPadding().value
            InternalRouter(
                currentInternalScreen,
                context = context,
                internalNavController = internalNavController,
                homeViewModel = homeViewModel
            )
        }
    }
}

