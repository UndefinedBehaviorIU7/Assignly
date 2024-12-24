package com.example.assignly.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.assignly.presentation.addGroup.AddGroup
import com.example.assignly.presentation.login.Login
import com.example.assignly.presentation.signup.Signup
import com.example.assignly.presentation.taskList.TaskViewModel
import com.example.assignly.presentation.taskList.TasksList
import com.example.assignly.ui.theme.AssignlyTheme
import com.example.assignly.presentation.addtask.AddTask
import com.example.assignly.presentation.groupList.GroupListScreen
import com.example.assignly.presentation.infoGroup.infoGroup

enum class Navigation(val route: String) {
    LOGIN("login"),
    SIGNUP("signup"),
    TASK_LIST("task_list"),
    ADD_TASK("add_task"),
    ADD_GROUP("add_group"),
    GROUP_LIST("group_list"),
    INFO_GROUP("info_group")
}

@ExperimentalStdlibApi
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

                    composable(
                        route = "${Navigation.ADD_TASK}/{groupId}/{token}",
                        arguments = listOf(navArgument("groupId") { type = NavType.IntType },
                            navArgument("token") {type = NavType.StringType}
                        )
                    ) { backStackEntry ->
                        val groupId = backStackEntry.arguments?.getInt("groupId")
                        val token = backStackEntry.arguments?.getString("token")
                        AddTask(navController = navController, groupId = groupId!!, token!!)
                    }

                    composable(Navigation.SIGNUP.toString()) {
                        Signup(navController)
                    }

                    composable(Navigation.GROUP_LIST.toString()) {
                        GroupListScreen(navController)
                    }

                    composable(Navigation.INFO_GROUP.toString()) {
                        infoGroup(navController)
                    }

                    composable(
                        route = "${Navigation.TASK_LIST}/{groupId}",
                        arguments = listOf(navArgument("groupId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val groupId = backStackEntry.arguments?.getInt("groupId")
                        TasksList(navController = navController, groupId = groupId!!)
                    }

                    composable(Navigation.ADD_GROUP.toString()) {
                        AddGroup(navController = navController)
                    }
                }
            }

        }
    }
}
