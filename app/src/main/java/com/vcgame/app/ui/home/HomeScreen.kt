@file:JvmName("HomeGraphKt")

package com.vcgame.app.ui.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vcgame.app.Routes

/**
 * Defines the nested navigation graph for the Home section of the application.
 * This Composable manages navigation *within* the home-related screens.
 *
 * @param rootNavController The NavController from MainActivity, used for top-level navigation (e.g., logout).
 * @param initialUsername The username passed from the main graph to the start destination of this nested graph.
 */
@Composable
fun HomeScreen(
    rootNavController: NavHostController, // Pass the main NavController for app-level navigation
    initialUsername: String // Pass the username from the main graph
) {
    // This NavController manages navigation *within* the Home graph
    val homeNavController = rememberNavController()

    NavHost(
        navController = homeNavController,
        startDestination = HomeGraphRoutes.DASHBOARD
    ) {
        composable(HomeGraphRoutes.DASHBOARD) {
            DashboardScreen(
                username = initialUsername, // Use the username passed from the root graph
                onGoToProfile = { homeNavController.navigate(HomeGraphRoutes.PROFILE) },
                onGoToSettings = { homeNavController.navigate(HomeGraphRoutes.SETTINGS) },
                onGoToDashboard = { homeNavController.navigate(HomeGraphRoutes.DASHBOARD) },
                onLogout = {
                    // Navigate back to the Login screen in the main graph
                    // This clears the entire main graph back stack and navigates to LOGIN
                    rootNavController.navigate(Routes.LOGIN) {
                        // Pop everything off the main graph's back stack up to the root, inclusively
                        popUpTo(rootNavController.graph.id) { inclusive = true }
                    }
                }
            )
        }

        composable(HomeGraphRoutes.PROFILE) {
            ProfileScreen(
                onGoToSettings = { homeNavController.navigate(HomeGraphRoutes.SETTINGS) }
            )
        }

        composable(HomeGraphRoutes.SETTINGS) {
            SettingsScreen()
        }
        // Add more composable destinations for other home-related screens here
    }
}
