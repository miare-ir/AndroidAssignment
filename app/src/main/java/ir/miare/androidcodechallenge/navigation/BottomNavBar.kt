package ir.miare.androidcodechallenge.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import ir.miare.androidcodechallenge.core.designsystem.component.AppNavigationBar
import ir.miare.androidcodechallenge.core.designsystem.component.AppNavigationBarItem
import ir.miare.androidcodechallenge.feature.followedplayers.navigation.followedPlayersNavigationRoute
import ir.miare.androidcodechallenge.feature.home.navigation.homeNavigationRoute

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: @Composable () -> Unit
) {
    object Home : BottomNavItem(
        route = homeNavigationRoute,
        title = "Home",
        icon = {
            Icon(
                imageVector = Icons.Rounded.Home,
                contentDescription = "icon home"
            )
        }
    )

    object Followed : BottomNavItem(
        route = followedPlayersNavigationRoute,
        title = "Followed",
        icon = {
            Icon(
                imageVector = Icons.Rounded.Favorite,
                contentDescription = "icon followed"
            )
        }
    )
}

val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Followed,
)

@Composable
fun BottomNavBar(
    onNavigateToDestination: (BottomNavItem) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    AppNavigationBar {
        bottomNavItems.forEachIndexed { index, item ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(item)
            AppNavigationBarItem(
                selected = selected,
                onClick = {
                    onNavigateToDestination(item)
                },
                icon = item.icon,
                modifier = modifier.testTag(item::class.java.simpleName),
                label = { Text(text = item.title) },
                alwaysShowLabel = false
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: BottomNavItem) =
    this?.hierarchy?.any {
        it.route?.contains(destination.route, true) ?: false
    } ?: false