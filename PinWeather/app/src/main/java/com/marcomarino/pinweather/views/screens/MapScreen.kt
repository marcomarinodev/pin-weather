package com.marcomarino.pinweather.views.screens

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.marcomarino.pinweather.R
import com.marcomarino.pinweather.model.SessionManager
import com.marcomarino.pinweather.model.entities.LightLocation
import com.marcomarino.pinweather.viewmodels.PostScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapScreenViewModel: ViewModel() {

    val currentPostBitmapString = mutableStateOf<String>("")

    @WorkerThread
    fun getLocationImage(id: String, onComplete: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                currentPostBitmapString.value = SessionManager.locationsDao.getLocationImage(id)
            } catch (e: Exception) {
                Log.e("DB-ERR", e.message.toString())
            }
            onComplete()
        }
    }
}

@Composable
fun MapScreen(vm: MapScreenViewModel) {

    val markers = remember { mutableStateListOf<LightLocation>() }
    val openDialog = remember { mutableStateOf(false) }

    @WorkerThread
    suspend fun getMarkers() {
        withContext(Dispatchers.IO) {
            try {
                markers.clear()
                markers.addAll(SessionManager.locationsDao.getLightLocations())
            } catch (e: Exception) {
                Log.e("DB-ERR", e.message.toString())
            }
        }
    }

    LaunchedEffect(Unit, block = {
        getMarkers()
    })

    Box(contentAlignment = Alignment.Center) {

        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = { openDialog.value = false },
                buttons = {
                    Row(
                        modifier = Modifier.padding(all = 8.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = colorResource(id = R.color.primary),
                                contentColor = Color.White
                            ),
                            onClick = { openDialog.value = false }
                        ) {
                            Text(text = "Close")
                        }
                    }
                },
                text = {
                    Image(
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(4.dp))
                            .fillMaxWidth()
                            .height(200.dp),
                        bitmap = PostScreenViewModel.decodeImage(vm.currentPostBitmapString.value).asImageBitmap() ,
                        contentDescription = ""
                    )
                }
            )
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = colorResource(id = R.color.white)
        ) {
            val center = LatLng(42.654807963082845, 12.009035008535934)
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(center, 2f)
            }

            GoogleMap(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = SessionManager.bottomNavigationHeight.dp),
                cameraPositionState = cameraPositionState,
            ) {

                markers.forEach { marker ->
                    Marker(
                        state = MarkerState(
                            position = LatLng(
                                marker.lat.toDouble(),
                                marker.lon.toDouble()
                            )
                        ),
                        title = "Photo",
                        snippet = "Lat",
                        onClick = {
                            vm.getLocationImage(marker.uuid) {
                                openDialog.value = true
                            }
                            true
                        }
                    )
                }
            }
        }
    }
}