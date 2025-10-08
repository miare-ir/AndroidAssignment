package ir.miare.androidcodechallenge.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import ir.miare.androidcodechallenge.AppState
import ir.miare.androidcodechallenge.feature.followedplayers.navigation.followedPlayersScreen
import ir.miare.androidcodechallenge.feature.home.navigation.homeNavigationRoute
import ir.miare.androidcodechallenge.feature.home.navigation.homeScreen

@Composable
fun AppNavHost(
    appState: AppState,
    modifier: Modifier = Modifier,
    startDestination: String = homeNavigationRoute,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        homeScreen()
        followedPlayersScreen()
    }
}