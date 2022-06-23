package com.marcomarino.pinweather.views.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.marcomarino.pinweather.R
import com.marcomarino.pinweather.viewmodels.SignUpViewModel
import com.marcomarino.pinweather.views.components.AppButton
import com.marcomarino.pinweather.views.components.FormField
import com.marcomarino.pinweather.views.components.TopBar

@Composable
fun SignUpScreen(navController: NavHostController, vm: SignUpViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        SignUpView(navController = navController, vm = vm)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SignUpView(navController: NavHostController, vm: SignUpViewModel) {

    val fullName = remember { mutableStateOf(TextFieldValue()) }
    val email = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val confirmPassword = remember { mutableStateOf(TextFieldValue()) }

    Scaffold(
        topBar = {
            TopBar(
                navController = navController,
                title = "Sign Up",
                showBackIcon = true,
                showLogo = false
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // MAIN LOGO
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(colorResource(id = R.color.background)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "",
                        tint = colorResource(id = R.color.primary),
                        modifier = Modifier
                            .size(56.dp)
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                // FULL NAME FIELD
                FormField(
                    label = "Full Name",
                    textState = fullName,
                    error = vm.fullNameError.value,
                    keyboardType = KeyboardType.Text,
                    onValueChanged = {
                        vm.onFullNameChanged(fullName = fullName.value.text)
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // EMAIL FIELD
                FormField(
                    label = "Email",
                    textState = email,
                    error = vm.emailError.value,
                    keyboardType = KeyboardType.Email,
                    onValueChanged = {
                        vm.onEmailChanged(email = email.value.text)
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // PASSWORD FIELD
                FormField(
                    label = "New Password",
                    textState = password,
                    error = vm.passwordError.value,
                    keyboardType = KeyboardType.Password,
                    onValueChanged = {
                        vm.onPasswordChanged(password = password.value.text)
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // CONFIRM PASSWORD FIELD
                FormField(
                    label = "Confirm Password",
                    textState = confirmPassword,
                    error = vm.confirmPasswordError.value,
                    keyboardType = KeyboardType.Password,
                    onValueChanged = {
                        vm.onConfirmPasswordChanged(
                            password = password.value.text,
                            confirmPassword = confirmPassword.value.text
                        )
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                AppButton(text = "Sign Up") {
                    vm.register(
                        fullName = fullName.value.text,
                        email = email.value.text,
                        password = password.value.text
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                if (vm.errorMessage.isNotEmpty()) {
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
    )
}