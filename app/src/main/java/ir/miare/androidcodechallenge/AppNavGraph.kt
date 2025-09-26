package ir.miare.androidcodechallenge

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ir.miare.androidcodechallenge.feature.fallow.FollowedRoute
import ir.miare.androidcodechallenge.feature.fallow.followedNavigation
import ir.miare.androidcodechallenge.feature.league.LeaguesRoute
import ir.miare.androidcodechallenge.feature.league.leaguesNavigation
import ir.miare.androidcodechallenge.feature.player.PlayersRoute
import ir.miare.androidcodechallenge.feature.player.playersNavigation
import ir.miare.androidcodechallenge.feature.team.TeamsRoute
import ir.miare.androidcodechallenge.feature.team.teamsNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Football Players") }) },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = route == PlayersRoute::class.qualifiedName,
                    onClick = {
                        navController.navigate(PlayersRoute)
                    },
                    icon = {
                        Icon(
                            Icons.AutoMirrored.Filled.List,
                            contentDescription = null
                        )
                    },
                    label = { Text("Players") }
                )
                NavigationBarItem(
                    selected = route == LeaguesRoute::class.qualifiedName,
                    onClick = { navController.navigate(LeaguesRoute) },
                    icon = {
                        Icon(
                            Icons.AutoMirrored.Filled.List,
                            contentDescription = null
                        )
                    },
                    label = { Text("Leagues") }
                )
                NavigationBarItem(
                    selected = route == TeamsRoute::class.qualifiedName,
                    onClick = { navController.navigate(TeamsRoute) },
                    icon = {
                        Icon(
                            Icons.AutoMirrored.Filled.List,
                            contentDescription = null
                        )
                    },
                    label = { Text("Teams") }
                )
                NavigationBarItem(
                    selected = route == FollowedRoute::class.qualifiedName,
                    onClick = { navController.navigate(FollowedRoute) },
                    icon = {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = null
                        )
                    },
                    label = { Text("Followed") }
                )
            }
        }
    ) { padding ->
        Surface(Modifier.fillMaxSize()) {
            NavHost(
                navController = navController,
                startDestination = PlayersRoute,
                modifier = Modifier.padding(padding)
            ) {
                playersNavigation()
                leaguesNavigation()
                teamsNavigation()
                followedNavigation()
            }
        }
    }
}