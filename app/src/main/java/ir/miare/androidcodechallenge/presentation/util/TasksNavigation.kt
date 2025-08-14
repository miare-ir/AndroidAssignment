package ir.miare.androidcodechallenge.presentation.util

import RankingScreen
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ir.miare.androidcodechallenge.presentation.followed_players.FollowedPlayersScreen

fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation<MyFootMobScreens.MyFootMob>(startDestination = MyFootMobScreens.LeagueInfo) {
        composable<MyFootMobScreens.LeagueInfo> {
            RankingScreen()
        }
        composable<MyFootMobScreens.FollowedPlayers> {
            FollowedPlayersScreen {}
        }
    }
}
