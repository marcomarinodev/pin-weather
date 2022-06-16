package com.marcomarino.pinweather.views.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

    val openDialog = remember { mutableStateOf(false) }
    val selectedEntry = remember { mutableStateOf("") }
    
    LaunchedEffect(Unit, block = {
        vm.call()
    })
    
    Box {
        if (vm.errorMessage.isEmpty()) {
            WeatherCardList(entries = vm.weatherList, context = context, onSelected = { weatherEntry ->
                selectedEntry.value = weatherEntry.id
                openDialog.value = true
            })
        } else {
            Text(vm.errorMessage)
        }
        
        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = { openDialog.value = false },
                title = { Text("Do you want to remove this item?") },
                buttons = {
                    Row(
                        modifier = Modifier.padding(all = 8.dp).fillMaxWidth(),
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