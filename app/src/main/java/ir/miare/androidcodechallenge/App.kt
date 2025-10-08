package ir.miare.androidcodechallenge

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import ir.miare.androidcodechallenge.core.designsystem.component.AppTopAppBar
import ir.miare.androidcodechallenge.navigation.AppNavHost
import ir.miare.androidcodechallenge.navigation.BottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    appState: AppState = rememberAppState()
) {

    Scaffold(
        topBar = {

            AppTopAppBar(
                modifier = Modifier.testTag("AppTopAppBar"),
                navigationIcon = android.R.drawable.ic_dialog_info,
                titleImage = android.R.drawable.ic_dialog_info,
                bgColor = Color.Gray,
                navigationIconContentDescription = "App TopAppBar navigation icon bank login",
                actionIcon = android.R.drawable.ic_dialog_info,
                actionIconContentDescription = "App TopAppBar action icon avatar",
            )
        },
        bottomBar = {
            BottomNavBar(
                onNavigateToDestination = appState::navigateToTopLevelDestination,
                currentDestination = appState.currentDestination,
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            AppNavHost(appState = appState)
        }
    }
}
