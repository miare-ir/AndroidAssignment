package ir.miare.androidcodechallenge.feature.player

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object PlayersRoute

fun NavGraphBuilder.playersNavigation() {
    composable<PlayersRoute> {
        PlayersScreen()
    }
}