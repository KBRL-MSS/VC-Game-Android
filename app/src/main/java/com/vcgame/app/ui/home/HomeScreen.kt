@file:JvmName("HomeGraphKt")

package com.vcgame.app.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vcgame.app.Routes
import com.vcgame.app.ui.party.CreatePartyScreen
import com.vcgame.app.ui.party.JoinPartyScreen
import com.vcgame.app.ui.party.PartyDetailsScreen
import com.vcgame.app.ui.game.TicTacToeGameScreen
import com.vcgame.app.ui.theme.AppTheme

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
                onGoToCreateParty = { homeNavController.navigate(HomeGraphRoutes.CREATE_PARTY)},
                onGoToJoinParty = {homeNavController.navigate(HomeGraphRoutes.JOIN_PARTY)},
                onPlay = {homeNavController.navigate(HomeGraphRoutes.START_GAME)},
                onLogout = {
                    // Navigate back to the Login screen in the main graph
                    // This clears the entire main graph back stack and navigates to LOGIN
                    rootNavController.navigate(Routes.LOGIN) {
                        // Pop everything off the main graph's back stack up to the root, inclusively
                        popUpTo(rootNavController.graph.id) { inclusive = true }
                    }
                },
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

        composable (HomeGraphRoutes.CREATE_PARTY){
            CreatePartyScreen(
                onBack = {homeNavController.navigate(HomeGraphRoutes.DASHBOARD)},
                onCreatePartyClicked = {homeNavController.navigate(HomeGraphRoutes.PARTY_DETAILS)}
            )
        }

        composable (HomeGraphRoutes.PARTY_DETAILS){
            PartyDetailsScreen(
                partyId = null,
                onBack = {homeNavController.navigate(HomeGraphRoutes.DASHBOARD)},
                onStartGame = {homeNavController.navigate(HomeGraphRoutes.START_GAME)}
            )
        }

        composable (HomeGraphRoutes.JOIN_PARTY){
            JoinPartyScreen(
                onBack = {homeNavController.navigate(HomeGraphRoutes.DASHBOARD)},
                onJoinParty = {homeNavController.navigate(HomeGraphRoutes.PARTY_DETAILS)}
            )
        }

        composable (HomeGraphRoutes.START_GAME){
            TicTacToeGameScreen(
                onBack = {homeNavController.navigate(HomeGraphRoutes.DASHBOARD)}
            )
        }
        // Add more composable destinations for other home-related screens here
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    AppTheme {
        HomeScreen(
            rootNavController = rememberNavController(),
            initialUsername = "Gamer!"
        )
    }
}
