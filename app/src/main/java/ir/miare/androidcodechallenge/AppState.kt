package ir.miare.androidcodechallenge

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import ir.miare.androidcodechallenge.feature.followedplayers.navigation.navigateToFollowedPlayers
import ir.miare.androidcodechallenge.feature.home.navigation.navigateToHome
import ir.miare.androidcodechallenge.navigation.BottomNavItem

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController()
): AppState {
    return remember(
        navController
    ) {
        AppState(navController)
    }
}

@Stable
class AppState(
    val navController: NavHostController
) {

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    fun navigateToTopLevelDestination(topLevelDestination: BottomNavItem) {

        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (topLevelDestination) {
            BottomNavItem.Home -> navController.navigateToHome(topLevelNavOptions)
            BottomNavItem.Followed -> navController.navigateToFollowedPlayers(topLevelNavOptions)
        }
    }
}