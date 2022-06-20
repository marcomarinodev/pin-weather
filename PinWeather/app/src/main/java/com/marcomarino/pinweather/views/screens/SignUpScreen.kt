package com.marcomarino.pinweather.views.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.marcomarino.pinweather.R
import com.marcomarino.pinweather.viewmodels.SignUpViewModel
import com.marcomarino.pinweather.views.components.TopBar
import com.marcomarino.pinweather.views.components.AppButton
import com.marcomarino.pinweather.views.components.FormField

@Composable
fun SignUpScreen(navController: NavHostController, vm: SignUpViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        SignUpView(navController = navController, vm = vm)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SignUpView(navController: NavHostController, vm: SignUpViewModel) {

    val viewModel = remember { vm }

    val fullName = remember { mutableStateOf(TextFieldValue()) }
    val fullNameError by viewModel.fullNameError.collectAsState()

    val email = remember { mutableStateOf(TextFieldValue()) }
    val emailError by viewModel.emailError.collectAsState()

    val password = remember { mutableStateOf(TextFieldValue()) }
    val passwordError by viewModel.passwordError.collectAsState()

    val confirmPassword = remember { mutableStateOf(TextFieldValue()) }
    val confirmPasswordError by viewModel.confirmPasswordError.collectAsState()

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
                    error = fullNameError,
                    keyboardType = KeyboardType.Text,
                    onValueChanged = {
                        viewModel.onFullNameChanged(fullName = fullName.value.text)
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // EMAIL FIELD
                FormField(
                    label = "Email",
                    textState = email,
                    error = emailError,
                    keyboardType = KeyboardType.Email,
                    onValueChanged = {
                        viewModel.onEmailChanged(email = email.value.text)
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // PASSWORD FIELD
                FormField(
                    label = "New Password",
                    textState = password,
                    error = passwordError,
                    keyboardType = KeyboardType.Password,
                    onValueChanged = {
                        viewModel.onPasswordChanged(password = password.value.text)
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // CONFIRM PASSWORD FIELD
                FormField(
                    label = "Confirm Password",
                    textState = confirmPassword,
                    error = confirmPasswordError,
                    keyboardType = KeyboardType.Password,
                    onValueChanged = {
                        viewModel.onConfirmPasswordChanged(
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
            }
        }
    )
}