package com.marcomarino.pinweather.network.repositories

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.marcomarino.pinweather.model.SessionManager
import com.marcomarino.pinweather.model.Token
import com.marcomarino.pinweather.model.TokenValidation
import com.marcomarino.pinweather.model.User
import com.marcomarino.pinweather.model.dao.UserDefaultDao
import com.marcomarino.pinweather.model.entities.UserDefault
import com.marcomarino.pinweather.network.API
import com.marcomarino.pinweather.network.NetworkUtility
import com.marcomarino.pinweather.network.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * A repository class provides a clean API for data access to the
 * rest of the application.
 */
class AccountRepository(private val userDefaultDao: UserDefaultDao) {

    private val accountAPI: API.AccountAPI = RetrofitHelper.getInstance().create(API.AccountAPI::class.java)

    /***
     * If credentials are valid, it gives back a token. With this token
     * you can complete all operations.
     */
    suspend fun makeRequest(
        baseURL: String,
        fullName: String = "",
        email: String,
        password: String
    ): User? {

        val url = NetworkUtility.compileAccessUrl(
            baseURL = baseURL,
            fullName = fullName,
            email = email,
            password = password
        )

        Log.i("NET-CALL", url)
        val result = accountAPI.access(url)

        Log.i("NET-CALL", result.body().toString())

        return if (result.body()?.data?.login == null) {
            result.body()?.data?.registerUser
        } else result.body()?.data?.login
    }

    suspend fun validateToken(baseURL: String, callback: () -> Unit) {
        withContext(Dispatchers.IO) {
            val token = getToken()
            val url = NetworkUtility.compileValidationUrl(baseURL, token)
            val result = accountAPI.validateToken(url)

            if (result.body()?.data?.validateToken == true) {
                SessionManager.token = token
                callback()
            } else {
                println("Invalid token")
            }
        }
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread // room executes suspend queries off the main thread
    suspend fun insert(user: UserDefault) {
        userDefaultDao.insertUser(user)
    }

    @WorkerThread
    fun getToken(): String {
        val token = userDefaultDao.getLocalToken()
        SessionManager.token = token ?: ""
        println("token: $token")
        return token ?: ""
    }

    @WorkerThread
    fun getTableSize(): Int {
        return userDefaultDao.tableSize()
    }
}
