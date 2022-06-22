package com.marcomarino.pinweather.model

import android.graphics.Bitmap
import com.marcomarino.pinweather.model.dao.LocationsDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object SessionManager {
    lateinit var locationsDao: LocationsDao
    var bottomNavigationHeight: Float = 56.0F
    var token = ""
    lateinit var takePictureIntentDispatcher: () -> Unit
    val _imageBitmap = MutableStateFlow<Bitmap?>(null)
    val imageBitmap: StateFlow<Bitmap?> = _imageBitmap
}