package ir.miare.androidcodechallenge.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

private const val ROUTE_PLAYERS = "players"
private const val ROUTE_FOLLOWED = "followed"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.List, contentDescription = "Players") },
                    label = { Text("Players") },
                    selected = currentDestination?.hierarchy?.any { it.route == ROUTE_PLAYERS } == true,
                    onClick = {
                        navController.navigate(ROUTE_PLAYERS) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
                
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Favorite, contentDescription = "Followed") },
                    label = { Text("Followed") },
                    selected = currentDestination?.hierarchy?.any { it.route == ROUTE_FOLLOWED } == true,
                    onClick = {
                        navController.navigate(ROUTE_FOLLOWED) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ROUTE_PLAYERS,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(ROUTE_PLAYERS) {
                PlayerListRoute()
            }
            composable(ROUTE_FOLLOWED) {
                FollowedListRoute()
            }
        }
    }
}


