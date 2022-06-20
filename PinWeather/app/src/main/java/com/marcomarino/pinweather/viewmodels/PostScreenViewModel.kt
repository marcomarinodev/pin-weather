package com.marcomarino.pinweather.viewmodels

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcomarino.pinweather.network.NetworkUtility
import com.marcomarino.pinweather.network.repositories.PostRepository
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class PostScreenViewModel(private val repo: PostRepository): ViewModel() {

    val latitudeState = mutableStateOf(TextFieldValue())
    val longitudeState = mutableStateOf(TextFieldValue())

    val latitudeError = mutableStateOf("")
    val longitudeError = mutableStateOf("")

    val saveButtonState = mutableStateOf(false)

    private var latModified: Boolean = false
    private var lonModified: Boolean = false

    fun sendLocation(bitmap: String, lat: Float, long: Float, onCompleted: () -> Unit) {
        NetworkUtility.handleCall {
            viewModelScope.launch {
                repo.addPost(
                    bitmap,
                    lat,
                    long
                )

                onCompleted()
            }
        }
    }

    fun setSaveButton() {
        if (latModified && lonModified) {
            if (latitudeError.value.isEmpty() && longitudeError.value.isEmpty()) {
                saveButtonState.value = true
                return
            }
        }

        saveButtonState.value = false
    }

    fun checkLatitude() {
        latModified = true
        if (latitudeState.value.text.isEmpty()) {
            latitudeError.value = "Invalid Latitude"
        } else {
            latitudeError.value = ""
        }
        setSaveButton()
    }

    fun checkLongitude() {
        lonModified = true
        if (longitudeState.value.text.isEmpty()) {
            longitudeError.value = "Invalid Longitude"
        } else {
            longitudeError.value = ""
        }
        setSaveButton()
    }

    companion object {
        fun encodeImage(bm: Bitmap?): String {
            if (bm == null) return ""
            val baos = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val b = baos.toByteArray()
            return Base64.encodeToString(b, Base64.DEFAULT)
        }

        fun decodeImage(str: String): Bitmap {
            val imageBytes = Base64.decode(str, 0)
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        }
    }

}