package com.marcomarino.pinweather.model

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.marcomarino.pinweather.model.dao.LocationsDao
import com.marcomarino.pinweather.model.dao.UserDefaultDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService

object SessionManager {
    lateinit var locationsDao: LocationsDao
    var token = ""
    lateinit var takePictureIntentDispatcher: () -> Unit
    val _imageBitmap = MutableStateFlow<Bitmap?>(null)
    val imageBitmap: StateFlow<Bitmap?> = _imageBitmap
}