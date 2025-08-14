package ir.miare.androidcodechallenge.presentation.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import ir.miare.androidcodechallenge.domain.entity.Player
import ir.miare.androidcodechallenge.domain.usecase.SortMode
import ir.miare.androidcodechallenge.presentation.PlayersViewModel

@Composable
fun PlayersScreen(viewModel: PlayersViewModel, showOnlyFollowed: Boolean) {
    val pagingItems: LazyPagingItems<Player> = viewModel.pagedPlayers.collectAsLazyPagingItems()
    val followed by viewModel.followed.collectAsState()
    val sortMode by viewModel.sortMode.collectAsState()

    LaunchedEffect(showOnlyFollowed) {
        viewModel.setShowOnlyFollowed(showOnlyFollowed)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SortChips(
            currentSortMode = sortMode,
            onSortSelected = viewModel::setSortMode
        )
        Spacer(modifier = Modifier.height(8.dp))
        when (val state = pagingItems.loadState.refresh) {
            is LoadState.Loading -> BoxCentered { CircularProgressIndicator() }
            is LoadState.Error -> BoxCentered { Text(text = "Error: ${state.error.localizedMessage}") }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(pagingItems.itemCount) { id ->
                        val player = pagingItems[id]
                        if (player != null) {
                            val isFollowed = player.name in followed
                            PlayerRow(
                                player = player,
                                isFollowed = isFollowed,
                                onToggleFollow = { viewModel.onToggleFollow(player.name) }
                            )
                            Divider()
                        }
                    }
                    when (val append = pagingItems.loadState.append) {
                        is LoadState.Loading -> item { BoxCentered { CircularProgressIndicator() } }
                        is LoadState.Error -> item { BoxCentered { Text("Error: ${append.error.localizedMessage}") } }
                        else -> Unit
                    }
                }
            }
        }
    }
}

@Composable
fun PlayerRow(player: Player, isFollowed: Boolean, onToggleFollow: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = player.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(text = player.team.name, style = MaterialTheme.typography.bodySmall)
            Text(
                text = "${player.totalGoal} goals",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        IconButton(
            onClick = onToggleFollow,
            modifier = Modifier.testTag("follow_button")
        ) {
            val icon =
                if (isFollowed) Icons.Default.Star else Icons.Default.StarBorder
            Icon(imageVector = icon, contentDescription = "Follow button")
        }
    }
}

@Composable
fun SortChips(
    currentSortMode: SortMode,
    onSortSelected: (SortMode) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp), 
        modifier = Modifier.horizontalScroll(rememberScrollState())
    ) {
        AssistChip(
            onClick = { onSortSelected(SortMode.ALPHABETICAL) },
            label = { Text("A-Z") },
            colors = AssistChipDefaults.assistChipColors(
                containerColor = if (currentSortMode == SortMode.ALPHABETICAL) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                },
                labelColor = if (currentSortMode == SortMode.ALPHABETICAL) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        )
        AssistChip(
            onClick = { onSortSelected(SortMode.TEAM_AND_LEAGUE_RANK) },
            label = { Text("Team & League Rank") },
            colors = AssistChipDefaults.assistChipColors(
                containerColor = if (currentSortMode == SortMode.TEAM_AND_LEAGUE_RANK) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                },
                labelColor = if (currentSortMode == SortMode.TEAM_AND_LEAGUE_RANK) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        )
        AssistChip(
            onClick = { onSortSelected(SortMode.MOST_GOALS) },
            label = { Text("Most Goals") },
            colors = AssistChipDefaults.assistChipColors(
                containerColor = if (currentSortMode == SortMode.MOST_GOALS) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                },
                labelColor = if (currentSortMode == SortMode.MOST_GOALS) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        )
    }
}

@Composable
fun BoxCentered(content: @Composable () -> Unit) {
    androidx.compose.foundation.layout.Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) { content() }
}


