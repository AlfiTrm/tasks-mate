package com.projectpam.taskmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.projectpam.taskmate.ui.screen_home.HomeScreen
import com.projectpam.taskmate.ui.screen_login.LoginScreen
import com.projectpam.taskmate.ui.theme.TaskmateTheme
import com.projectpam.taskmate.viewmodel.AuthViewModel
import com.projectpam.taskmate.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TaskmateTheme {

                val navController = rememberNavController()
                val authViewModel: AuthViewModel = viewModel()
                val taskViewModel: TaskViewModel = viewModel()

                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {

                    composable("login") {
                        LoginScreen(
                            authViewModel = authViewModel,
                            onLoginSuccess = {
                                taskViewModel.loadTasks()
                                navController.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }

                    composable("home") {
                        HomeScreen(
                            taskViewModel = taskViewModel,
                            onLogout = {
                                authViewModel.logout()
                                navController.navigate("login") {
                                    popUpTo("home") { inclusive = true }
                                }
                            }
                        )
                    }

                }
            }
        }
    }
}
