package com.marcomarino.pinweather.views.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.marcomarino.pinweather.R

@Composable
fun TopBar(
    navController: NavHostController,
    title: String,
    showBackIcon: Boolean,
    showLogo: Boolean
) {
    TopAppBar(
        navigationIcon =
        if (showBackIcon && navController.previousBackStackEntry != null) {{
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }} else null,
        title = {
            Row {
                if (showLogo) {
                    Icon(
                        Icons.Default.Place,
                        contentDescription = "",
                        modifier = Modifier
                            .size(28.dp)
                    )
                }
                Text(text = title, fontSize = 24.sp)
            }
        },
        backgroundColor = colorResource(id = R.color.background),
        contentColor = colorResource(id = R.color.white)
    )
}