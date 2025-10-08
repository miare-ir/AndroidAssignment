package ir.miare.androidcodechallenge.feature.followedplayers.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ir.miare.androidcodechallenge.feature.followedplayers.FollowedPlayersScreenRoute

const val followedPlayersNavigationRoute = "followed_players_route"

fun NavController.navigateToFollowedPlayers(navOptions: NavOptions? = null){
    this.navigate(followedPlayersNavigationRoute,navOptions)
}

fun NavGraphBuilder.followedPlayersScreen(
){
    composable(
        route = followedPlayersNavigationRoute
    ) {
        FollowedPlayersScreenRoute()
    }
}