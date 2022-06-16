package com.marcomarino.pinweather.views.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.marcomarino.pinweather.R
import com.marcomarino.pinweather.navigation.Routes

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        Routes.Home,
        Routes.Maps,
        Routes.Post
    )
    var selectedItem = remember { mutableStateOf(Routes.Home.route) }

    BottomNavigation(
        backgroundColor = colorResource(id = R.color.background),
        contentColor = colorResource(id = R.color.white)
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    if (item.icon != null)Icon(painterResource(id = item.icon), contentDescription = item.route)
                    },
                selectedContentColor = colorResource(id = R.color.secondary),
                unselectedContentColor = colorResource(id = R.color.white),
                selected = item.route == selectedItem.value,
                onClick = {
                    navController.navigate(item.route) {

                        selectedItem.value = item.route

                        // pop up to the start destination of the graph
                        // to avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }

                        // avoid multiple copies of the same destination when selecting
                        // again the same item
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}