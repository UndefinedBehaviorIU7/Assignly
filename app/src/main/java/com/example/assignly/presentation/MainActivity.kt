package com.example.assignly.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.assignly.presentation.addGroup.AddGroup
import com.example.assignly.presentation.login.Login
import com.example.assignly.presentation.signup.Signup
import com.example.assignly.presentation.taskList.TaskViewModel
import com.example.assignly.presentation.taskList.TasksList
import com.example.assignly.ui.theme.AssignlyTheme

enum class Navigation(val route: String) {
    LOGIN("login"),
    SIGNUP("signup"),
    TASK_LIST("task_list"),
    ADD_GROUP("add_group")
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
                    startDestination = Navigation.ADD_GROUP.toString()
                ) {
                    composable(Navigation.LOGIN.toString()) {
                        Login(navController)
                    }

                    composable(Navigation.SIGNUP.toString()) {
                        Signup(navController)
                    }

                    composable(Navigation.TASK_LIST.toString()) {
                        val token = "1"
                        val groupId = 21
                        val taskViewModel: TaskViewModel by viewModels()
                        TasksList(navController = navController, token = token, groupId = groupId, vm = taskViewModel)
                    }

                    composable(Navigation.ADD_GROUP.toString()) {
                        AddGroup(navController = navController)
                    }
                }
            }

        }
    }
}
