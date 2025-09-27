package ir.miare.androidcodechallenge.feature.fallow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.miare.androidcodechallenge.core.domain.usecases.GetFollowedPlayersUseCase
import ir.miare.androidcodechallenge.core.domain.usecases.SetPlayerFollowedUseCase
import ir.miare.androidcodechallenge.core.model.PlayerWithDetails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowedViewModel @Inject constructor(
    private val setPlayerFollowed: SetPlayerFollowedUseCase,
    getFollowedPlayers: GetFollowedPlayersUseCase,
) : ViewModel() {
    val uiState: StateFlow<FollowedUiState> = getFollowedPlayers()
        .map<List<PlayerWithDetails>, FollowedUiState> { FollowedUiState.Success(it) }
        .onStart { emit(FollowedUiState.Loading) }
        .catch { emit(FollowedUiState.Error(it.message ?: "Failed to load followed players")) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FollowedUiState.Loading)

    fun onFollowClicked(playerId: String, follow: Boolean) {
        viewModelScope.launch {
            setPlayerFollowed(playerId, follow)
        }
    }
}

sealed interface FollowedUiState {
    data object Loading : FollowedUiState
    data class Success(val data: List<PlayerWithDetails>) : FollowedUiState
    data class Error(val message: String) : FollowedUiState
}