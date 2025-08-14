package ir.miare.androidcodechallenge.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.miare.androidcodechallenge.presentation.util.BottomNavigationBar
import ir.miare.androidcodechallenge.presentation.util.MyFootMobScreens
import ir.miare.androidcodechallenge.presentation.util.mainGraph

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFootMobTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = MyFootMobScreens.MyFootMob,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        mainGraph(navController)
                    }
                }
            }
        }
    }
}