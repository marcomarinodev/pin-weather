package com.marcomarino.pinweather.views.components

import android.content.Context
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.marcomarino.pinweather.model.WeatherEntry

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WeatherCardList(
    entries: List<WeatherEntry>,
    context: Context,
    onSelected: ((WeatherEntry) -> Unit)? = null
) {

    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        items(entries) {
                weatherEntry ->
            Button(
                elevation = null,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White.copy(0.0f)),
                onClick = {
                    if (onSelected != null) {
                        onSelected(weatherEntry)
                    }
                }
                ) {

                WeatherCard(entry = weatherEntry, context = context)

            }
        }
    }

}