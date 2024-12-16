package com.example.assignly.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.assignly.presentation.login.Login
import com.example.assignly.presentation.signup.Signup
import com.example.assignly.ui.theme.AssignlyTheme

enum class Navigation(val route: String) {
    LOGIN("login"),
    SIGNUP("signup")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AssignlyTheme (dynamicColor = false){
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Navigation.LOGIN.toString()
                ) {
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
}
