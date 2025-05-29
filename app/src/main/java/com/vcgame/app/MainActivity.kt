package com.vcgame.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vcgame.app.ui.login.LoginScreen
import com.vcgame.app.ui.signup.SignUpScreen
import com.vcgame.app.ui.home.HomeScreen
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.vcgame.app.ui.theme.AppTheme

// Define routes for navigation
object Routes {
    const val LOGIN = "login"
    const val HOME = "home/{username}"
    const val SIGNUP = "signup"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Setup the navigation controller
                    val navController = rememberNavController()

                    // Define the navigation graph
                    NavHost(navController = navController, startDestination = Routes.LOGIN) {
                        // Login screen composable
                        composable(Routes.LOGIN) {
                            LoginScreen(
                                onLoginClicked = { username, password ->
                                    navController.navigate(Routes.HOME.replace("{username}", username)) {
                                        // Pop up to the login screen to prevent going back to login
                                        // after successful login.
                                        popUpTo(Routes.LOGIN) { inclusive = true }
                                    }
                                },
                                onSignUpClicked = {
                                    // Navigate to the signup screen
                                    navController.navigate(Routes.SIGNUP)
                                }
                            )
                        }

                        // Home screen composable, expecting a username argument
                        composable(Routes.HOME) { backStackEntry ->
                            // Retrieve the username argument from the navigation back stack
                            val username = backStackEntry.arguments?.getString("username") ?: "Guest"
                            HomeScreen(rootNavController = navController, initialUsername = username)
                        }

                        //SignUp Page
                        composable(Routes.SIGNUP) {
                            SignUpScreen(
                                onSignUpClicked = { username, password, confirmPassword ->
                                    navController.navigate(Routes.HOME.replace("{username}", username)) {
                                        // Clear all screens up to login, then clear login too.
                                        // This makes Home the new start destination.
                                        popUpTo(Routes.LOGIN) { inclusive = true }
                                    }
                                    // Alternative: Go back to Login after signup
                                    navController.popBackStack()
                                },
                                onSignInClicked = {
                                    // Navigate back to the login screen
                                    navController.popBackStack() // Pops the SignUp screen off the stack
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
