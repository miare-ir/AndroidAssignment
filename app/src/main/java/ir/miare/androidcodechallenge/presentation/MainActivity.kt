package ir.miare.androidcodechallenge.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import ir.miare.androidcodechallenge.presentation.league_data_list.RankingScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFootMobTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    RankingScreen()
                }
            }
        }
    }
}