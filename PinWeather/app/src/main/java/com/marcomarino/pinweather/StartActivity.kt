package com.marcomarino.pinweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
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
