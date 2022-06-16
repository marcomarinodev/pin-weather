package com.marcomarino.pinweather.views.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.marcomarino.pinweather.R
import com.marcomarino.pinweather.model.WeatherEntry
import androidx.compose.ui.res.colorResource

fun getWeatherEntryImage(status: String): Pair<Int, Int> {
    return when (status) {
        "cloudy" -> Pair(R.drawable.cloudy, R.color.secondary)
        "partly_cloudy" -> Pair(R.drawable.partly_cloudy, R.color.secondary)
        "sunny" -> Pair(R.drawable.sunny, R.color.primary)
        "lightning" -> Pair(R.drawable.stormy, R.color.secondary)
        "rainy" -> Pair(R.drawable.rainy, R.color.secondary)
        "snow" -> Pair(R.drawable.snow, R.color.secondary)
        "foggy" -> Pair(R.drawable.foggy, R.color.secondary)
        else -> Pair(R.drawable.sunny, R.color.primary)
    }
}

@Composable
fun WeatherCard(entry: WeatherEntry, context: Context) {

    val entryImage = getWeatherEntryImage(entry.status)

    Card(
        elevation = 10.dp,
        modifier = Modifier.padding(10.dp),
        shape = RoundedCornerShape(15.dp),
    ) {
        Box {
            Image(
                painter = painterResource(id = entryImage.first),
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .height(150.dp)
                    .blur(1.dp)
            )

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row (Modifier.fillMaxWidth()) {
                    Column {
                        Text(
                            entry.city,
                            fontSize = 25.sp,
                            color = colorResource(id = entryImage.second)
                        )
                        Text(
                            entry.time,
                            fontSize = 20.sp,
                            color = colorResource(id = entryImage.second)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = entry.temp.toString() + "°",
                        fontSize = 40.sp,
                        color = colorResource(id = entryImage.second)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row (Modifier.fillMaxWidth()) {
                    //  Text(entryResponse.status)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        "MAX: " + entry.max + "° MIN: " + entry.min + "°",
                        color = colorResource(id = entryImage.second)
                    )
                }
            }
        }
    }

}
