package ir.miare.androidcodechallenge.feature.team

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object TeamsRoute

fun NavGraphBuilder.teamsNavigation() {
    composable<TeamsRoute> {
        TeamsScreen()
    }
}