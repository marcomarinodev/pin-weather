package com.marcomarino.pinweather.viewmodels

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcomarino.pinweather.MainActivity
import com.marcomarino.pinweather.model.SessionManager
import com.marcomarino.pinweather.model.entities.UserDefault
import com.marcomarino.pinweather.network.API
import com.marcomarino.pinweather.network.NetworkUtility
import com.marcomarino.pinweather.network.NetworkUtility.Companion.isValidEmail
import com.marcomarino.pinweather.network.NetworkUtility.Companion.isValidPassword
import com.marcomarino.pinweather.network.repositories.AccountRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class SignUpViewModel(
    val context: WeakReference<Context>,
    private val repo: AccountRepository
): ViewModel() {

    private val _fullNameError = MutableStateFlow<String>("")
    val fullNameError: StateFlow<String> = _fullNameError

    private val _emailError = MutableStateFlow<String>("")
    val emailError: StateFlow<String> = _emailError

    private val _passwordError = MutableStateFlow<String>("")
    val passwordError: StateFlow<String> = _passwordError

    private val _confirmPasswordError = MutableStateFlow<String>("")
    val confirmPasswordError: StateFlow<String> = _confirmPasswordError

    var errorMessage: String by mutableStateOf("")

    fun onFullNameChanged(fullName: String) {
        _fullNameError.value = if (fullName.isEmpty()) "Full name cannot be empty" else ""
    }

    fun onEmailChanged(email: String) {
        _emailError.value = if (!email.isValidEmail()) "Invalid email" else ""
    }

    fun onPasswordChanged(password: String) {
        _passwordError.value = if (!password.isValidPassword())
            "1 lowercase & uppercase character + 1 digit + 1 special character + size 8" else ""
    }

    fun onConfirmPasswordChanged(password: String, confirmPassword: String) {
        _confirmPasswordError.value = if
                (confirmPassword != password)
                    "Confirmation password must be equal to new password" else ""
    }

    fun register(fullName: String, email: String, password: String) {

        if (context.get() != null) {
            errorMessage = NetworkUtility.checkConnection(context.get()!!) ?: ""
            if (errorMessage.isNotEmpty()) return
        }

        viewModelScope.launch {

            val result = repo.accessRequest(
                baseURL = API.AccountAPI.SIGNUP_URL,
                fullName = fullName,
                email = email,
                password = password
            )

            if (result == null) {
                errorMessage = "Registration failed!"
            } else {
                // We have to save the login token
                errorMessage = ""
                Log.i("USER-REG", result.fullName)

                repo.insert(
                    UserDefault(
                        uuid = result.id,
                        token = result.token,
                        fullName = result.fullName,
                        email = result.email
                    )
                )

                SessionManager.token = result.token

                presentHome()
            }
        }

    }

    private fun presentHome() {
        context.get()?.startActivity(Intent(context.get(), MainActivity::class.java))
    }
}