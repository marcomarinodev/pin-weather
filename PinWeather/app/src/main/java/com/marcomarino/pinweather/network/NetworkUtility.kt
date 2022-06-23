package com.marcomarino.pinweather.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Patterns.EMAIL_ADDRESS
import java.util.regex.Pattern

class NetworkUtility {
    companion object {

        fun compileAccessUrl(
            baseURL: String,
            fullName: String = "",
            email: String,
            password: String
        ): String {
            return baseURL
                .replace(
                    if (fullName.isNotEmpty()) "{fullName}" else "",
                    fullName
                )
                .replace("{email}", email)
                .replace("{password}", password)
        }

        fun compileValidationUrl(
            baseURL: String,
            token: String
        ): String {
            return baseURL
                .replace("{token}", token)
        }

        fun compileSearchUrl(
            baseURL: String,
            token: String,
            query: String
        ): String {
            return baseURL
                .replace("{token}", token)
                .replace("{query}", query)
        }

        fun compilePlaceOpUrl(
            baseURL: String,
            token: String,
            id: String
        ): String {
            return baseURL
                .replace("{token}", token)
                .replace("{id}", id)
        }

        fun String.isValidEmail(): Boolean {
            return this.isNotEmpty() && EMAIL_ADDRESS.matcher(this).matches()
        }

        fun String.isValidPassword(): Boolean {
            /*
            * Password must contain:
            * at least one digit [0-9]
            * at least one lowercase and uppercase character
            * at least one special character
            * min: 8, max: 20
            * */
            val passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~\$^+=<>]).{8,20}\$")
            return this.isNotEmpty() && passwordPattern.matcher(this).matches()
        }

        fun checkConnection(context: Context) : String? {
            if (!isNetworkConnected(context)) {
                return "Check your internet connection"
            }

            return null
        }

        private fun isNetworkConnected(context: Context): Boolean {

            // get connectivity manager from the context in parameter
            // we use the connectivity manager to query the state of network
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val activeNetwork = connectivityManager.activeNetwork ?: return false

                // capabilities of the active network
                val networkCapabilities = connectivityManager
                    .getNetworkCapabilities(activeNetwork) ?: return false

                return when {
                    // Cellular connectivity or
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    // WiFi connectivity
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    // otherwise return false
                    else -> false
                }
            } else {
                // suppress if the android version is below M
                @Suppress("DEPRECATION") val networkInfo =
                    connectivityManager.activeNetworkInfo ?: return false
                @Suppress("DEPRECATION")
                return networkInfo.isConnected
            }

        }
    }
}