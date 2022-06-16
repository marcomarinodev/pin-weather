package com.marcomarino.pinweather.views.components

import android.content.Context
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.marcomarino.pinweather.model.WeatherEntry
import com.marcomarino.pinweather.views.components.WeatherCard

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