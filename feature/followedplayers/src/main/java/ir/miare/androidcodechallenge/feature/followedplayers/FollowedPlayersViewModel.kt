package ir.miare.androidcodechallenge.feature.followedplayers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.miare.androidcodechallenge.core.domain.FollowPlayerUseCase
import ir.miare.androidcodechallenge.core.domain.GetFollowedPlayersUseCase
import ir.miare.androidcodechallenge.core.domain.UnFollowPlayerUseCase
import ir.miare.androidcodechallenge.core.model.Player
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class FollowedPlayersViewModel @Inject constructor(
    private val getFollowedPlayersUseCase: GetFollowedPlayersUseCase,
    private val followPlayerUseCase: FollowPlayerUseCase,
    private val unFollowPlayerUseCase: UnFollowPlayerUseCase,
) : ViewModel() {
    val uiState: StateFlow<FollowedPlayersUiState> =
        getFollowedPlayersUseCase()
            .map { list ->
                if (list.isEmpty()) FollowedPlayersUiState.Empty else FollowedPlayersUiState.Success(
                    list
                )
            }
            .onStart { emit(FollowedPlayersUiState.Loading) }
            .catch { e ->
                emit(
                    FollowedPlayersUiState.Error(
                        e,
                        e.message ?: "Failed to load followed players"
                    )
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = FollowedPlayersUiState.Loading
            )

    fun follow(player: Player) {
        viewModelScope.launch {
            try {
                followPlayerUseCase(player)
                // If your source is a Room Flow, uiState updates automatically.
            } catch (t: Throwable) {
                // Optionally surface a one-shot event/snackbar here.
            }
        }
    }

    fun unfollow(player: Player) {
        viewModelScope.launch {
            try {
                unFollowPlayerUseCase(player)
            } catch (t: Throwable) {
                // Optionally surface a one-shot event/snackbar here.
            }
        }
    }
}