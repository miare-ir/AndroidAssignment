package ir.miare.androidcodechallenge.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ir.miare.androidcodechallenge.presentation.PlayersViewModel
import ir.miare.androidcodechallenge.presentation.ui.theme.AppTheme

@Composable
fun AppRoot() {
    AppTheme {
        val navController = rememberNavController()
        val items = listOf("all", "followed")
        
        val viewModel: PlayersViewModel = hiltViewModel()
        
        Scaffold(
            bottomBar = {
                NavigationBar {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route
                    items.forEach { screen ->
                        NavigationBarItem(
                            selected = currentRoute == screen,
                            onClick = {
                                navController.navigate(screen) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.List,
                                    contentDescription = null
                                )
                            },
                            label = { Text(text = screen.replaceFirstChar { it.uppercase() }) }
                        )
                    }
                }
            }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = "all",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                composable("all") {
                    PlayersScreen(viewModel = viewModel, showOnlyFollowed = false)
                }
                composable("followed") {
                    PlayersScreen(viewModel = viewModel, showOnlyFollowed = true)
                }
            }
        }
    }
}


