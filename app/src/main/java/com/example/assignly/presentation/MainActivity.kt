package com.example.assignly.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.assignly.R
import com.example.assignly.presentation.login.Login
import com.example.assignly.presentation.signup.Signup

enum class Navigation (val route: String) {
    LOGIN("login"),
    SIGNUP("signup")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController,
                startDestination = stringResource(R.string.login_nav)) {
                composable(Navigation.LOGIN.toString()) {
                    Login(navController)
                }

                composable(Navigation.SIGNUP.toString()) {
                    Signup(navController)
                }
            }
        }
    }
}
