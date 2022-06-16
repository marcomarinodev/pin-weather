package com.marcomarino.pinweather.views.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.marcomarino.pinweather.R

@Composable
fun AppButton(text: String, action: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(40.dp, 0.dp, 40.dp, 0.dp)
    ) {
        Button(
            onClick = action,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.background)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = text, color = colorResource(id = R.color.black))
        }
    }
}