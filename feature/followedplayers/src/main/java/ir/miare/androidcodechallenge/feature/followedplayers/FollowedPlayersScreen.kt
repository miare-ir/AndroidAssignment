package ir.miare.androidcodechallenge.feature.followedplayers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ir.miare.androidcodechallenge.core.model.Player

/**
 * Route-level composable:
 * - Collects state from the ViewModel
 * - Hoists UI state + callbacks into a stateless screen
 */
@Composable
internal fun FollowedPlayersScreenRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    viewModel: FollowedPlayersViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FollowedPlayersScreen(
        modifier = modifier,
        uiState = uiState,
        onBackClick = onBackClick,
        onFollowClick = viewModel::follow,
        onUnfollowClick = viewModel::unfollow,
    )
}

/**
 * Stateless screen:
 * - Receives state + event callbacks from caller (Route)
 * - Renders UI only
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FollowedPlayersScreen(
    modifier: Modifier = Modifier,
    uiState: FollowedPlayersUiState,
    onBackClick: () -> Unit,
    onFollowClick: (Player) -> Unit,
    onUnfollowClick: (Player) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Followed Players") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        when (uiState) {
            is FollowedPlayersUiState.Loading -> LoadingSection(
                modifier = modifier.padding(innerPadding)
            )

            is FollowedPlayersUiState.Empty -> EmptySection(
                modifier = modifier.padding(innerPadding)
            )

            is FollowedPlayersUiState.Error -> ErrorSection(
                modifier = modifier.padding(innerPadding),
                message = uiState.message ?: "Something went wrong"
            )

            is FollowedPlayersUiState.Success -> PlayersListSection(
                modifier = modifier.padding(innerPadding),
                players = uiState.players,
                onUnfollowClick = onUnfollowClick,
                onFollowClick = onFollowClick, // kept for symmetry; not used in UI list below
            )
        }
    }
}

/* ---------- Sections (stateless) ---------- */

@Composable
private fun LoadingSection(modifier: Modifier = Modifier) {
    androidx.compose.foundation.layout.Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptySection(modifier: Modifier = Modifier) {
    androidx.compose.foundation.layout.Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "You haven't followed any players yet.",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun ErrorSection(
    modifier: Modifier = Modifier,
    message: String
) {
    androidx.compose.foundation.layout.Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ElevatedCard(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
private fun PlayersListSection(
    modifier: Modifier = Modifier,
    players: List<Player>,
    onUnfollowClick: (Player) -> Unit,
    onFollowClick: (Player) -> Unit, // not used here but kept for future reuse
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(players, key = { it.id }) { player ->
            PlayerCard(
                player = player,
                onUnfollowClick = { onUnfollowClick(player) }
            )
        }
    }
}

@Composable
private fun PlayerCard(
    player: Player,
    onUnfollowClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = player.name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.padding(top = 4.dp))
            Text(
                text = "Team: ${player.team.name}  •  Goals: ${player.totalGoal}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.padding(top = 12.dp))
            FilledTonalButton(onClick = onUnfollowClick) {
                Text("Unfollow")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FollowedPlayersScreenPreview(
) {
    val uiState = FollowedPlayersUiState.Success(emptyList())
    FollowedPlayersScreen(
        uiState = uiState,
        onBackClick = {},
        onFollowClick = {},
        onUnfollowClick = {},
    )
}
