package com.marcomarino.pinweather.views.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.marcomarino.pinweather.R
import com.marcomarino.pinweather.model.SessionManager
import com.marcomarino.pinweather.viewmodels.PostScreenViewModel
import com.marcomarino.pinweather.views.components.FormField

@Composable
fun PostScreen(vm: PostScreenViewModel) {

    val viewModel = remember { vm }
    val imageBitmap by SessionManager.imageBitmap.collectAsState()

    val latitudeState = remember { mutableStateOf(TextFieldValue()) }
    val latitudeError by viewModel.latitudeError.collectAsState()

    val longitudeState = remember { mutableStateOf(TextFieldValue()) }
    val longitudeError by viewModel.longitudeError.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.primary)
            ),
            onClick = { SessionManager.takePictureIntentDispatcher() }) {
            Icon(
                tint = Color.White,
                imageVector = Icons.Default.Camera,
                contentDescription = "Camera",
            )
        }

        if (imageBitmap != null) {
            Image(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(4.dp))
                    .fillMaxWidth()
                    .height(200.dp),
                bitmap = imageBitmap!!.asImageBitmap(),
                contentDescription = "",
            )

            Spacer(modifier = Modifier.height(20.dp))

            FormField(
                label = "Latitude",
                textState = latitudeState,
                error = latitudeError,
                keyboardType = KeyboardType.Number,
                onValueChanged = {}
            )

            Spacer(modifier = Modifier.height(20.dp))

            FormField(
                label = "Longitude",
                textState = longitudeState,
                error = longitudeError,
                keyboardType = KeyboardType.Number,
                onValueChanged = {}
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { viewModel.sendLocation(
                    viewModel.encodeImage(imageBitmap),
                    latitudeState.value.text.toFloat(),
                    longitudeState.value.text.toFloat()
                ) },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.background)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Send Post", color = colorResource(id = R.color.primary))
            }
        }
    }

}
