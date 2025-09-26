package ir.miare.androidcodechallenge.feature.fallow

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object FollowedRoute

fun NavGraphBuilder.followedNavigation() {
    composable<FollowedRoute> {
        FollowedScreen()
    }
}