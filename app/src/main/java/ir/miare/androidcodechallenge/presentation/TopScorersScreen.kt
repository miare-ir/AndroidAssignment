package ir.miare.androidcodechallenge.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ir.miare.androidcodechallenge.R
import ir.miare.androidcodechallenge.core.presentation.UiState
import ir.miare.androidcodechallenge.core.presentation.asString
import ir.miare.androidcodechallenge.presentation.composables.PlayersList
import ir.miare.androidcodechallenge.presentation.composables.SortSection

@Composable
fun TopScorersScreen(
    viewModel: TopScorersViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(state.orderBy) {
        viewModel.onIntent(TopScorersScreenIntents.UpdateTopScorers)
    }

    TopScorersScreenContent(
        state = state,
        onEvent = viewModel::onIntent,
    )
}

@Composable
private fun TopScorersScreenContent(
    state: TopScorersUiState,
    onEvent: (TopScorersScreenIntents) -> Unit,
) {
    TopScorersScaffold(
        content = {
            when (state.topScorers) {
                is UiState.Failure -> {
                    Text(
                        text = state.topScorers.error.asString(),
                        color = MaterialTheme.colorScheme.error
                    )
                }

                is UiState.Loading -> {
                    CircularProgressIndicator()
                }

                is UiState.Success -> {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        SortSection(
                            onSortChange = { order ->
                                onEvent(TopScorersScreenIntents.OnOrderList(orderBy = order))
                            }, orderState = state.orderBy
                        )
                        PlayersList(
                            topScorers = state.topScorers.data,
                            onPlayerClick = { playerId ->

                            }
                        )
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopScorersScaffold(
    content: @Composable BoxScope.() -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.top_scorers_top_app_bar_title))
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
            content = { content() }
        )
    }
}

