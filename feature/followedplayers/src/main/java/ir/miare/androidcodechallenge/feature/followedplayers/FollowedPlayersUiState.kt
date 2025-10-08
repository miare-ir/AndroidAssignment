package ir.miare.androidcodechallenge.feature.followedplayers

import ir.miare.androidcodechallenge.core.model.Player

sealed interface FollowedPlayersUiState {
    data object Loading : FollowedPlayersUiState
    data object Empty : FollowedPlayersUiState
    data class Success(val players: List<Player>) : FollowedPlayersUiState
    data class Error(val throwable: Throwable, val message: String) : FollowedPlayersUiState
}