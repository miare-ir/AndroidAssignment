package ir.miare.androidcodechallenge.feature.followedplayers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ir.miare.androidcodechallenge.core.model.Player
import ir.miare.androidcodechallenge.core.model.stableKey
import ir.miare.androidcodechallenge.core.ui.EmptySection
import ir.miare.androidcodechallenge.core.ui.ErrorSection
import ir.miare.androidcodechallenge.core.ui.LoadingSection
import ir.miare.androidcodechallenge.core.ui.PlayerCard
import ir.miare.androidcodechallenge.core.ui.Title

@Composable
internal fun FollowedPlayersScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: FollowedPlayersViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FollowedPlayersScreen(
        modifier = modifier,
        uiState = uiState,
        onUnfollowClick = viewModel::unfollow,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FollowedPlayersScreen(
    modifier: Modifier = Modifier,
    uiState: FollowedPlayersUiState,
    onUnfollowClick: (Player) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Spacer(Modifier.height(10.dp))

        Title(
            modifier,
            text = "Followed Players"
        )

        when (uiState) {
            is FollowedPlayersUiState.Loading ->
                LoadingSection()

            is FollowedPlayersUiState.Empty ->
                EmptySection(
                    text = "You haven't followed any players yet."
                )

            is FollowedPlayersUiState.Error ->
                ErrorSection(message = uiState.message)

            is FollowedPlayersUiState.Success ->
                PlayersListSection(
                    players = uiState.players,
                    onUnfollowClick = onUnfollowClick,
                )
        }
    }
}


@Composable
private fun PlayersListSection(
    modifier: Modifier = Modifier,
    players: List<Player>,
    onUnfollowClick: (Player) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(players, key = { it.stableKey() }) { player ->
            PlayerCard(
                item = player,
                onFollowClick = onUnfollowClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FollowedPlayersScreenPreview(
    @PreviewParameter(FollowedPlayersUiStatePreviewParameterProvider::class)
    followedPlayersUiState: FollowedPlayersUiState,
) {
    FollowedPlayersScreen(
        uiState = followedPlayersUiState,
        onUnfollowClick = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun FollowedPlayersScreenEmptyStatePreview(
) {
    val uiState = FollowedPlayersUiState.Empty
    FollowedPlayersScreen(
        uiState = uiState,
        onUnfollowClick = {},
    )
}
