package com.marcomarino.pinweather.views.screens

import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.marcomarino.pinweather.R

@Composable
fun AppTextFieldColors(): TextFieldColors {
    return TextFieldDefaults.textFieldColors(
        textColor = colorResource(id = R.color.primary),
        focusedIndicatorColor = colorResource(id = R.color.primary),
        focusedLabelColor = colorResource(id = R.color.primary),
        cursorColor = colorResource(id = R.color.primary)
    )
}