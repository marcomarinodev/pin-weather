package com.marcomarino.pinweather.views.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.marcomarino.pinweather.R
import com.marcomarino.pinweather.viewmodels.HomeViewModel
import com.marcomarino.pinweather.views.components.WeatherCardList

@Composable
fun HomeScreen(context: Context, vm: HomeViewModel) {

    val openDialog = rememberSaveable { mutableStateOf(false) }
    val selectedEntry = rememberSaveable { mutableStateOf("") }
    val isLoading = remember { vm.isLoading }
    
    LaunchedEffect(Unit, block = {
        vm.call()
    })
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {

        if (isLoading.value) {
            CircularProgressIndicator(color = colorResource(id = R.color.primary))
        }

        if (vm.errorMessage.value.isEmpty() && !isLoading.value) {
            WeatherCardList(entries = vm.weatherList, context = context, onSelected = { weatherEntry ->
                selectedEntry.value = weatherEntry.id
                openDialog.value = true
            })
        } else {
            Text(vm.errorMessage.value)
        }

        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = { openDialog.value = false },
                title = { Text("Do you want to remove this item?") },
                buttons = {
                    Row(
                        modifier = Modifier
                            .padding(all = 8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = colorResource(id = R.color.error),
                                contentColor = Color.White
                            ),
                            onClick = {
                                vm.deleteFavEntry(selectedEntry.value) {
                                    openDialog.value = false
                                    vm.call()
                                }
                            }
                        ) {
                            Text(text = "Delete")
                        }
                        
                        Spacer(modifier = Modifier.padding(horizontal = 8.dp))

                        Button(
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = colorResource(id = R.color.primary),
                                contentColor = Color.White
                            ),
                            onClick = { openDialog.value = false }
                        ) {
                            Text(text = "Dismiss")
                        }
                    }
                }
            )
        }
    }
    
}