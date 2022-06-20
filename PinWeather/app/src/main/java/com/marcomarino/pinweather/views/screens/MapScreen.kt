package com.marcomarino.pinweather.views.screens

import android.net.Network
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.marcomarino.pinweather.R
import com.marcomarino.pinweather.model.SessionManager
import com.marcomarino.pinweather.model.WeatherEntry
import com.marcomarino.pinweather.model.entities.LightLocation
import com.marcomarino.pinweather.network.NetworkUtility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class MapScreenViewModel: ViewModel() {

    private val _markers = mutableListOf<LightLocation>()
    val markers: List<LightLocation>
        get() = _markers


}

@Composable
fun MapScreen(vm: MapScreenViewModel) {

    val markers = remember { mutableStateListOf<LightLocation>() }

    @WorkerThread
    suspend fun getMarkers() {
        withContext(Dispatchers.IO) {
            try {
                markers.clear()
                markers.addAll(SessionManager.locationsDao.getLightLocations())
            } catch (e: Exception) {
                Log.e("ERR", e.message.toString())
            }
        }
    }

    LaunchedEffect(Unit, block = {
        getMarkers()
    })

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorResource(id = R.color.white)
    ) {
        val center = LatLng(42.654807963082845, 12.009035008535934)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(center, 2f)
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize().padding(bottom = SessionManager.bottomNavigationHeight.dp),
            cameraPositionState = cameraPositionState,
        ) {
            markers.forEach { marker ->
                Marker(
                    state = MarkerState(position = LatLng(marker.lat.toDouble(),
                        marker.lon.toDouble()
                    )),
                    title = "Photo",
                    snippet = "Lat"
                )
            }
        }
    }
}