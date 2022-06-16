package com.marcomarino.pinweather.routers

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.marcomarino.pinweather.model.dao.UserDefaultDao
import com.marcomarino.pinweather.navigation.Routes
import com.marcomarino.pinweather.network.repositories.AccountRepository
import com.marcomarino.pinweather.viewmodels.LoginViewModel
import com.marcomarino.pinweather.viewmodels.SignUpViewModel
import com.marcomarino.pinweather.views.screens.LoginScreen
import com.marcomarino.pinweather.views.screens.SignUpScreen

@Composable
fun StartRouter(context: Context, userDefaultDao: UserDefaultDao) {

    val navController = rememberNavController()
    val accountRepository = AccountRepository(userDefaultDao = userDefaultDao)
    val loginViewModel = LoginViewModel(context, accountRepository)
    val signUpViewModel = SignUpViewModel(context, accountRepository)

    NavHost(navController = navController, startDestination = Routes.Login.route) {

        composable(route = Routes.Login.route) {
            LoginScreen(
                navController = navController,
                vm = loginViewModel
            )
        }

        composable(route = Routes.SignUp.route) {
            SignUpScreen(
                navController = navController,
                vm = signUpViewModel
            )
        }

    }
}