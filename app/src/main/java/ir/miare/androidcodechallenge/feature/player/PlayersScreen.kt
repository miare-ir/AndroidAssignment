package ir.miare.androidcodechallenge.feature.player

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ir.miare.androidcodechallenge.core.common.component.PlayerCard
import ir.miare.androidcodechallenge.core.model.SortMode

@Composable
fun PlayersScreen(
    viewModel: PlayersViewModel = hiltViewModel()
) {
    val sortMode by viewModel.sortMode.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pageNumber by viewModel.pageNumber.collectAsStateWithLifecycle()

    PlayersScreen(
        sortMode = sortMode,
        uiState = uiState,
        pageNumber = pageNumber,
        onSearch = viewModel::onSearch,
        onSortSelected = viewModel::onSortSelected,
        onFollowClicked = viewModel::onFollowClicked,
        prevPage = viewModel::prevPage,
        nextPage = viewModel::nextPage
    )
}

@Composable
internal fun PlayersScreen(
    sortMode: SortMode,
    uiState: PlayersUiState,
    pageNumber: Int,
    onSearch: (String) -> Unit,
    onSortSelected: (SortMode) -> Unit,
    onFollowClicked: (String, Boolean) -> Unit,
    prevPage: () -> Unit,
    nextPage: () -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        SearchAndSortBar(
            onSearch = onSearch
        )
        SortChips(
            sortMode = sortMode,
            onSelect = onSortSelected,
        )
        when (uiState) {
            is PlayersUiState.Loading -> {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    CircularProgressIndicator()
                    Text(
                        "  Loading players...",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            is PlayersUiState.Error -> {
                val message = uiState.message
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Error: $message", color = MaterialTheme.colorScheme.error)
                    Row(Modifier.padding(top = 8.dp)) {
                        Button(onClick = { /* retry by nudging flow: re-emit pageIndex */ nextPage(); prevPage() }) {
                            Text(
                                "Retry"
                            )
                        }
                    }
                }
            }

            is PlayersUiState.Success -> {
                val players = uiState.data
                LazyColumn(contentPadding = PaddingValues(16.dp)) {
                    item { Text("Players", style = MaterialTheme.typography.titleMedium) }
                    items(players) { p ->
                        PlayerCard(p) { follow ->
                            onFollowClicked(
                                p.playerId,
                                follow
                            )
                        }
                    }
                    item {
                        PaginationBar(
                            page = pageNumber,
                            onPrev = prevPage,
                            onNext = nextPage
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchAndSortBar(
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val (query, setQuery) = remember { mutableStateOf("") }
    Column(modifier = modifier) {
        OutlinedTextField(
            value = query,
            onValueChange = {
                setQuery(it)
                onSearch(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            placeholder = { Text("Search players, teams, leagues") }
        )
    }
}

@Composable
private fun SortChips(
    sortMode: SortMode,
    onSelect: (SortMode) -> Unit
) {
    val items = listOf(
        SortMode.DEFAULT to "Default",
        SortMode.LEAGUE_RANK to "League rank",
        SortMode.TEAM_RANK to "Team rank",
        SortMode.GOALS_SCORED to "Goals",
        SortMode.LEAGUE_GOAL_AVG to "Goal avg",
    )
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items.forEach { (mode, label) ->
            val selected = mode == sortMode
            FilterChip(
                selected = selected,
                onClick = { onSelect(mode) },
                label = { Text(label) },
                modifier = Modifier.padding(end = 8.dp),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    }
}

@Composable
private fun PaginationBar(
    page: Int,
    onPrev: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Button(onClick = onPrev, colors = ButtonDefaults.buttonColors()) { Text("Prev") }
        Text(
            "Page $page",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Button(onClick = onNext, colors = ButtonDefaults.buttonColors()) { Text("Next") }
    }
}
