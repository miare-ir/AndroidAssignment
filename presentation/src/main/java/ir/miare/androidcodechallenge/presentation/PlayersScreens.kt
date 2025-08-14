@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package ir.miare.androidcodechallenge.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ir.miare.androidcodechallenge.domain.model.SortMode
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun PlayerListRoute(viewModel: PlayersViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            Column(Modifier.fillMaxWidth().padding(16.dp)) {
                Text("Sorting by:", style = MaterialTheme.typography.titleMedium)
                SortBar(state.sortMode, onChange = viewModel::setSortMode)
            }
        }
    ) { padding ->
        if (state.isLoading) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        } else {

            val listState = rememberLazyListState()

            LaunchedEffect(state.items.firstOrNull()?.id) {
                listState.scrollToItem(0)
            }

            LaunchedEffect(listState) {
                snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                    .distinctUntilChanged()
                    .collect { lastVisibleItemIndex ->
                        val totalItems = listState.layoutInfo.totalItemsCount
                        val buffer = 5
                        if (
                            lastVisibleItemIndex != null &&
                            lastVisibleItemIndex >= totalItems - buffer &&
                            !state.isLoadingNextPage
                        ) {
                            viewModel.loadNextPage()
                        }
                    }
            }

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(state.items, key = { it.id.value }) { player ->
                    PlayerRow(player)
                    HorizontalDivider()
                }
                if (state.isLoadingNextPage) {
                    item {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SortBar(current: SortMode, onChange: (SortMode) -> Unit) {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        FilterChip(
            selected = current == SortMode.NONE,
            onClick = { onChange(SortMode.NONE) },
            label = { Text("None") }
        )
        FilterChip(
            selected = current == SortMode.TEAM_AND_LEAGUE_RANK,
            onClick = { onChange(SortMode.TEAM_AND_LEAGUE_RANK) },
            label = { Text("Team & league ranking") }
        )
        FilterChip(
            selected = current == SortMode.MOST_GOALS,
            onClick = { onChange(SortMode.MOST_GOALS) },
            label = { Text("Most goals") }
        )
        FilterChip(
            selected = current == SortMode.LEAGUE_AVG_GOALS,
            onClick = { onChange(SortMode.LEAGUE_AVG_GOALS) },
            label = { Text("League avg") }
        )
    }
}

@Composable
fun PlayerRow(player: PlayerItemUi, viewModel: PlayersViewModel = hiltViewModel()) {
    Column(Modifier.fillMaxWidth().padding(16.dp)) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = player.name, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
            AssistChip(onClick = { viewModel.onToggleFollow(player.id) }, label = { Text(if (player.isFollowed) "Unfollow" else "Follow") })
        }
        Spacer(Modifier.height(4.dp))
        Text(text = "${player.teamName} (r${player.teamRank}) • ${player.leagueName} (r${player.leagueRank})")
        Text(text = "Goals: ${player.totalGoals}", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun FollowedListRoute(viewModel: PlayersViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()
    val followed by viewModel.followedUi.collectAsState()
    Scaffold(topBar = { TopAppBar(title = { Text("Followed Players") }) }) { padding ->
        if (state.isLoading) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        } else {
            if (followed.isEmpty()) {
                Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) { Text("No followed players yet") }
            } else {
                LazyColumn(Modifier.fillMaxSize().padding(padding)) {
                    items(followed, key = { it.id.value }) { player ->
                        PlayerRow(player)
                        Divider()
                    }
                }
            }
        }
    }
}


