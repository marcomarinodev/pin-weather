package com.marcomarino.pinweather.views.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.PhotoCamera
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.marcomarino.pinweather.R
import com.marcomarino.pinweather.model.SessionManager
import com.marcomarino.pinweather.viewmodels.PostScreenViewModel
import com.marcomarino.pinweather.views.components.FormField

@Composable
fun PostScreen(vm: PostScreenViewModel) {

    val imageBitmap by SessionManager.imageBitmap.collectAsState()

    val openDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit, block = {
        SessionManager._imageBitmap.value = null
    })

    Box {

        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = { openDialog.value = false },
                title = {
                    Text("Post successfully added")
                },
                text = {
                    Text("You can found it inside the global map as a mark")
                },
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
                            Text(text = "Ok")
                        }
                    }
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Create New Post",
                fontSize = 20.sp,
                color = colorResource(id = R.color.primary)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Box(contentAlignment = Alignment.BottomEnd) {
                if (imageBitmap != null) {
                    Image(
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(4.dp))
                            .fillMaxWidth()
                            .height(200.dp),
                        bitmap = imageBitmap!!.asImageBitmap(),
                        contentDescription = ""
                    )
                } else {
                    Canvas(modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp), onDraw = {
                        drawRect(color = Color.LightGray)
                    })
                }

                Button(
                    modifier = Modifier.padding(end = 8.dp, bottom = 4.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = R.color.primary)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        openDialog.value = false
                        // It creates a new thread, therefore is an async call
                        SessionManager.takePictureIntentDispatcher()
                    }) {
                    Row {
                        Icon(
                            tint = Color.White,
                            imageVector = Icons.Sharp.PhotoCamera,
                            contentDescription = "Camera",
                        )
                    }
                }
            }

            if (imageBitmap != null) {
                Column(horizontalAlignment = Alignment.Start) {
                    Spacer(modifier = Modifier.height(20.dp))

                    FormField(
                        label = "Latitude",
                        modifier = Modifier.fillMaxWidth(),
                        textState = vm.latitudeState,
                        error = vm.latitudeError.value,
                        keyboardType = KeyboardType.Number,
                        onValueChanged = {
                            vm.checkLatitude()
                        }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    FormField(
                        label = "Longitude",
                        modifier = Modifier.fillMaxWidth(),
                        textState = vm.longitudeState,
                        error = vm.longitudeError.value,
                        keyboardType = KeyboardType.Number,
                        onValueChanged = {
                            vm.checkLongitude()
                        }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            vm.sendLocation(
                                PostScreenViewModel.encodeImage(imageBitmap),
                                vm.latitudeState.value.text.toFloat(),
                                vm.longitudeState.value.text.toFloat()
                            ) { // on completed
                                openDialog.value = true
                                SessionManager._imageBitmap.value = null
                            }
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.background)
                        ),
                        enabled = vm.saveButtonState.value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(text = "Send Post", color = colorResource(id = R.color.primary))
                    }
                }
            }
        }
    }

}
