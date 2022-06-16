package com.marcomarino.pinweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.marcomarino.pinweather.application.PinWeatherApplication
import com.marcomarino.pinweather.model.SessionManager
import com.marcomarino.pinweather.routers.StartRouter

class StartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, true)
        SessionManager.locationsDao = (application as PinWeatherApplication).database.locationsDao()

        setContent {
            StartRouter(this, (application as PinWeatherApplication).database.userDefaultDao())
        }
    }
}
