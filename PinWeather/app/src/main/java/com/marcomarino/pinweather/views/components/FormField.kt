package com.marcomarino.pinweather.views.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import com.marcomarino.pinweather.R
import com.marcomarino.pinweather.views.screens.AppTextFieldColors

@Composable
fun FormField(
    label: String,
    modifier: Modifier? = null,
    textState: MutableState<TextFieldValue>,
    error: String,
    keyboardType: KeyboardType,
    onValueChanged: () -> Unit
    ) {

    Column {
        TextField(
            label = { Text(text = label) },
            modifier = modifier ?: Modifier,
            value = textState.value,
            colors = AppTextFieldColors(),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            onValueChange = {
                textState.value = it
                onValueChanged()
            }
        )

        if (error.isNotEmpty()) {
            Text(
                text = error,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.error)
                )
            )
        }
    }

}