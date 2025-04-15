package ir.miare.androidcodechallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ir.miare.androidcodechallenge.core.presentation.theme.AppTheme
import ir.miare.androidcodechallenge.presentation.TopScorersViewModel
import ir.miare.androidcodechallenge.presentation.TopScorersScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel: TopScorersViewModel by viewModels<TopScorersViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                TopScorersScreen(
                    viewModel = viewModel,
                )
            }
        }
    }
}
