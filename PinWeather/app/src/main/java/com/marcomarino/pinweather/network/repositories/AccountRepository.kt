package com.marcomarino.pinweather.network.repositories

import android.util.Log
import androidx.annotation.WorkerThread
import com.marcomarino.pinweather.model.SessionManager
import com.marcomarino.pinweather.model.User
import com.marcomarino.pinweather.model.dao.UserDefaultDao
import com.marcomarino.pinweather.model.entities.UserDefault
import com.marcomarino.pinweather.network.API
import com.marcomarino.pinweather.network.NetworkUtility
import com.marcomarino.pinweather.network.RetrofitHelper

/**
 * A repository class provides an API for data access to the
 * rest of the application. In this case I developed this repository
 * to handle access requests and token validation if you got one
 * inside your local db.
 */
class AccountRepository(private val userDefaultDao: UserDefaultDao) {

    // Retrofit Account API instance to make network calls
    private val accountAPI: API.AccountAPI = RetrofitHelper.getInstance().create(API.AccountAPI::class.java)

    /***
     * If credentials are valid, it gives back a token. With this token
     * you can achieve all operations. This function is suspendable and it
     * can only be executed inside a coroutine or another suspend function
     * again.
     */
    suspend fun accessRequest(
        baseURL: String,
        fullName: String = "",
        email: String,
        password: String
    ): User? {

        // Compiling the url request
        val url = NetworkUtility.compileAccessUrl(
            baseURL = baseURL,
            fullName = fullName,
            email = email,
            password = password
        )

        // The network call
        Log.i("NET-CALL", "Sending request at $url")
        val result = accountAPI.access(url)

        Log.i("NET-CALL", "Receiving: ${result.body().toString()}")
        val data = result.body()?.data

        return data?.login ?: data?.registerUser
    }

    suspend fun validateToken(baseURL: String, callback: () -> Unit) {
        Log.i("TOKEN", "Validating token in thread ${Thread.currentThread().name}")
        val token = getToken()
        val url = NetworkUtility.compileValidationUrl(baseURL, token)
        val result = accountAPI.validateToken(url)

        if (result.body()?.data?.validateToken == true) {
            SessionManager.token = token
            Log.i("TOKEN", "Executing return callback")
            callback()
        } else {
            Log.i("TOKEN", "Invalid token")
        }
    }

    @WorkerThread // room executes suspend queries off the main thread
    suspend fun insert(user: UserDefault) {
        userDefaultDao.insertUser(user)
    }

    @WorkerThread
    suspend fun cleanUserDefaults() {
        userDefaultDao.deleteCurrentUser()
    }

    @WorkerThread
    fun getToken(): String {
        val token = userDefaultDao.getLocalToken()
        SessionManager.token = token ?: ""
        Log.i("TOKEN", "Current token: $token")
        return token ?: ""
    }

}
