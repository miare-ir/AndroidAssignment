package ir.miare.androidcodechallenge.presentation

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ir.miare.androidcodechallenge.R
import ir.miare.androidcodechallenge.core.presentation.UiState
import ir.miare.androidcodechallenge.core.presentation.asString
import ir.miare.androidcodechallenge.core.presentation.theme.AppTheme
import ir.miare.androidcodechallenge.presentation.TopScorersScreenIntents.OnOrderList
import ir.miare.androidcodechallenge.presentation.TopScorersScreenIntents.OnPlayerSelected
import ir.miare.androidcodechallenge.presentation.TopScorersScreenIntents.UpdateTopScorers
import ir.miare.androidcodechallenge.presentation.composables.MessageSection
import ir.miare.androidcodechallenge.presentation.composables.PlayerInfoBottomSheetContent
import ir.miare.androidcodechallenge.presentation.composables.PlayersList
import ir.miare.androidcodechallenge.presentation.composables.SortSection
import ir.miare.androidcodechallenge.presentation.models.UiLeague
import ir.miare.androidcodechallenge.presentation.models.UiPlayer
import ir.miare.androidcodechallenge.presentation.models.UiTeam
import ir.miare.androidcodechallenge.presentation.models.UiTopScorers
import ir.miare.androidcodechallenge.presentation.utils.Constants.ERROR_MESSAGE_TAG
import ir.miare.androidcodechallenge.presentation.utils.Constants.LOADING_INDICATOR_TAG
import ir.miare.androidcodechallenge.presentation.utils.Constants.TOP_SCORERS_LIST_TAG
import ir.miare.androidcodechallenge.presentation.utils.Constants.TOP_SCORERS_ORDER_SECTION
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TopScorersScreen(
    viewModel: TopScorersViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(state.orderBy) {
        viewModel.onIntent(UpdateTopScorers)
    }

    TopScorersScreenContent(
        state = state,
        onEvent = viewModel::onIntent,
    )
}
@Composable
@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
internal fun TopScorersScreenContent(
    state: TopScorersUiState,
    onEvent: (TopScorersScreenIntents) -> Unit,
) {
    val scope = rememberCoroutineScope()

    TopScorersScaffold(
        content = {
            when (state.topScorers) {
                is UiState.Failure -> {
                    MessageSection(
                        modifier = Modifier.padding(horizontal = 16.dp).testTag(ERROR_MESSAGE_TAG),
                        isErrorMessage = true,
                        message = state.topScorers.error.asString()
                    )
                }

                is UiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.testTag(LOADING_INDICATOR_TAG),
                    )
                }

                is UiState.Success -> {
                    state.selectedPlayer?.let { selectedPlayer ->
                        PlayerModalSheet(
                            player = selectedPlayer,
                            scope = scope,
                            onDismiss = {
                                onEvent(OnPlayerSelected(selectedPlayer = null))
                            }
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        SortSection(
                            modifier = Modifier.testTag(TOP_SCORERS_ORDER_SECTION),
                            onSortChange = { order ->
                                onEvent(OnOrderList(orderBy = order))
                            }, orderState = state.orderBy
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        PlayersList(
                            modifier = Modifier.testTag(TOP_SCORERS_LIST_TAG),
                            topScorers = state.topScorers.data,
                            onPlayerClick = { player ->
                                onEvent(OnPlayerSelected(selectedPlayer = player))
                            }
                        )
                    }
                }
            }
        }
    )
}

private val fakeData = TopScorersUiState(
    topScorers = UiState.Success(
        data = List(4) {
            UiTopScorers(
                players = List(3) { index ->
                    UiPlayer(
                        name = "Player $index",
                        team = UiTeam(
                            name = "Team $index",
                            rank = index,
                        ),
                        totalGoal = index * 10,
                    )
                },
                league = UiLeague(
                    name = "League $it",
                    country = "Country $it",
                    rank = it,
                    totalMatches = it * 100,
                )
            )
        }
    ),
)

@PreviewLightDark
@Composable
private fun TopScorersScreenContentPreview() {
    AppTheme {
        TopScorersScreenContent(
            state = fakeData,
            onEvent = {}
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun PlayerModalSheet(
    player: UiPlayer,
    scope: CoroutineScope,
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        content = {
            PlayerInfoBottomSheetContent(
                modifier = Modifier.padding(horizontal = 16.dp),
                player = player,
                onCloseClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) onDismiss()
                    }
                }
            )
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

