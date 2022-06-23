package com.marcomarino.pinweather.viewmodels

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcomarino.pinweather.MainActivity
import com.marcomarino.pinweather.model.SessionManager
import com.marcomarino.pinweather.model.entities.UserDefault
import com.marcomarino.pinweather.network.API
import com.marcomarino.pinweather.network.NetworkUtility
import com.marcomarino.pinweather.network.repositories.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class LoginViewModel(
    val context: WeakReference<Context>,
    private val repo: AccountRepository
    ): ViewModel() {

    var errorMessage = mutableStateOf("")

    init {
        // first, validate the token
        // if token is valid then present home directly
        viewModelScope.launch(Dispatchers.IO) {
            Log.i("TOKEN", "Calling the token in thread ${Thread.currentThread().name}")
            repo.validateToken(API.AccountAPI.VALIDATE_TOKEN_URL) {
                // callback if success
                presentHome()
            }
        }
    }

    fun login(email: String, password: String) {

        if (context.get() != null) {
            errorMessage.value = NetworkUtility.checkConnection(context = context.get()!!) ?: ""
            if (errorMessage.value.isNotEmpty()) return
        }

        viewModelScope.launch(Dispatchers.IO) {

            Log.i("LOG-IN", "Called LogInn in thread ${Thread.currentThread().name}")
            val result = repo.accessRequest(
                baseURL = API.AccountAPI.LOGIN_URL,
                email = email,
                password = password
            )

            if (result == null) {
                errorMessage.value = "Access failed!"
            } else {
                errorMessage.value = ""

                Log.i("USER-ACCESS", result.fullName)

                // clean user defaults from previous access
                repo.cleanUserDefaults()

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

    private fun presentHome() {
        context.get()?.startActivity(Intent(context.get(), MainActivity::class.java))
    }

}