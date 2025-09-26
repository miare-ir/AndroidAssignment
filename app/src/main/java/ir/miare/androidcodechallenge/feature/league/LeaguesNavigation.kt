package ir.miare.androidcodechallenge.feature.league

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object LeaguesRoute

fun NavGraphBuilder.leaguesNavigation() {
    composable<LeaguesRoute> {
        LeaguesScreen()
    }
}