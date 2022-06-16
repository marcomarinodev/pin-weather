package com.marcomarino.pinweather.viewmodels

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.marcomarino.pinweather.MainActivity
import com.marcomarino.pinweather.model.SessionManager
import com.marcomarino.pinweather.model.entities.UserDefault
import com.marcomarino.pinweather.network.API
import com.marcomarino.pinweather.network.NetworkUtility
import com.marcomarino.pinweather.network.repositories.AccountRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    val context: Context,
    private val repo: AccountRepository
    ): ViewModel() {

    var errorMessage: String by mutableStateOf("")

    init {
        // first, validate the token
        // if token is valid then present home directly
        NetworkUtility.handleCall {
            viewModelScope.launch {
                repo.validateToken(API.AccountAPI.VALIDATE_TOKEN_URL) {
                    presentHome()
                }
            }
        }
    }

    fun insertUserDefaults(user: UserDefault) = viewModelScope.launch {
        repo.insert(user)
    }

    fun login(email: String, password: String) {
        NetworkUtility.handleCall {
            viewModelScope.launch {
                val result = repo.makeRequest(
                    baseURL = API.AccountAPI.LOGIN_URL,
                    email = email,
                    password = password
                )

                if (result == null) {
                    errorMessage = "Access failed!"
                } else {
                    errorMessage = ""

                    // insertUserDefaults(user = UserDefault((result,)))
                    Log.i("USER-ACCESS", result.fullName)

                    // saving user token
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
    }

    private fun presentHome() {
        context.startActivity(Intent(context, MainActivity::class.java))
    }

}