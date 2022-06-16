package com.marcomarino.pinweather.viewmodels

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcomarino.pinweather.network.API
import com.marcomarino.pinweather.network.NetworkUtility
import com.marcomarino.pinweather.network.repositories.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class PostScreenViewModel(private val repo: PostRepository): ViewModel() {

    private val _latitudeError = MutableStateFlow<String>("")
    val latitudeError: StateFlow<String> = _latitudeError

    private val _longitudeError = MutableStateFlow<String>("")
    val longitudeError: StateFlow<String> = _longitudeError

    fun sendLocation(bitmap: String, lat: Float, long: Float) {
        NetworkUtility.handleCall {
            viewModelScope.launch {

                repo.addPost(
                    bitmap,
                    lat,
                    long
                )

            }
        }
    }

    fun encodeImage(bm: Bitmap?): String {
        if (bm == null) return ""
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    fun decodeImage(str: String): Bitmap? {
        val imageBytes = Base64.decode(str, 0)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

}