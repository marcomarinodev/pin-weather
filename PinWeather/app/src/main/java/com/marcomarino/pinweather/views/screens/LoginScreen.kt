package com.marcomarino.pinweather.views.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.marcomarino.pinweather.R
import com.marcomarino.pinweather.navigation.Routes
import com.marcomarino.pinweather.viewmodels.LoginViewModel
import com.marcomarino.pinweather.views.components.AppButton

@Composable
fun LoginScreen(navController: NavHostController, vm: LoginViewModel) {

    val email = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // LOGO
        Row {
            Icon(
                Icons.Default.Place,
                contentDescription = "",
                tint = colorResource(id = R.color.primary),
                modifier = Modifier
                    .size(36.dp)
            )
            Text(
                text = "PinWeather", style = TextStyle(
                    fontSize = 28.sp,
                    color = colorResource(id = R.color.primary)
                ),
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // EMAIL FIELD
        TextField(
            label = { Text(text = "Email") },
            value = email.value,
            colors = AppTextFieldColors(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = { email.value = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // PASSWORD FIELD
        TextField(
            label = { Text(text = "Password") },
            value = password.value,
            colors = AppTextFieldColors(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { password.value = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // SIGN UP
        Row {
            ClickableText(
                text = AnnotatedString("Sign Up here"),
                style = TextStyle(textDecoration = TextDecoration.Underline),
                onClick = {
                    navController.navigate(Routes.SignUp.route)
                })
            Text(", if you don't have an account")
        }

        Spacer(modifier = Modifier.height(20.dp))

        AppButton(text = "Login") {
            vm.login(email.value.text, password.value.text)
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (!vm.errorMessage.isNullOrEmpty()) {
            Text(
                text = vm.errorMessage,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.error)
                )
            )
        }

    }
}
