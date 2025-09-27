package ir.miare.androidcodechallenge.feature.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.miare.androidcodechallenge.core.domain.usecases.GetPlayersPagedUseCase
import ir.miare.androidcodechallenge.core.domain.usecases.GetSortModeUseCase
import ir.miare.androidcodechallenge.core.domain.usecases.SetPlayerFollowedUseCase
import ir.miare.androidcodechallenge.core.domain.usecases.SetSortModeUseCase
import ir.miare.androidcodechallenge.core.model.SortMode
import ir.miare.androidcodechallenge.core.model.networkmodel.PlayerModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(
    private val getPlayersPaged: GetPlayersPagedUseCase,
    private val setPlayerFollowed: SetPlayerFollowedUseCase,
    private val setSortMode: SetSortModeUseCase,
    getSortMode: GetSortModeUseCase,
) : ViewModel() {

    val sortMode: StateFlow<SortMode> = getSortMode()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            SortMode.DEFAULT
        )

    private val pageSize = 20
    private val pageIndex = MutableStateFlow(0)
    private val searchQuery = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<PlayersUiState> =
        combine(sortMode, pageIndex) { mode, page -> mode to page }
            .flatMapLatest { (mode, page) ->
                getPlayersPaged(
                    pageSize = pageSize,
                    offset = page * pageSize,
                    sortKey = mode.storageKey,
                )
            }
            .combine(searchQuery) { list, query ->
                val searchedString = query.trim().lowercase()
                if (searchedString.isEmpty()) list else list.filter { player ->
                    player.name.lowercase().contains(searchedString) ||
                            player.teamName.lowercase().contains(searchedString) ||
                            player.leagueName.lowercase().contains(searchedString)
                }
            }
            .map<List<PlayerModel>, PlayersUiState> { PlayersUiState.Success(it) }
            .onStart { emit(PlayersUiState.Loading) }
            .catch { emit(PlayersUiState.Error(it.message ?: "Something went wrong")) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PlayersUiState.Loading)

    fun onSortSelected(mode: SortMode) {
        viewModelScope.launch { setSortMode(mode) }
    }

    fun onFollowClicked(playerId: String, follow: Boolean) {
        viewModelScope.launch { setPlayerFollowed(playerId, follow) }
    }

    fun onSearch(query: String) {
        searchQuery.value = query
    }

    fun nextPage() {
        pageIndex.value = pageIndex.value + 1
    }

    fun prevPage() {
        pageIndex.value = (pageIndex.value - 1).coerceAtLeast(0)
    }

    val pageNumber: StateFlow<Int> = pageIndex.map { it + 1 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 1)
}

sealed interface PlayersUiState {
    data object Loading : PlayersUiState
    data class Success(val data: List<PlayerModel>) : PlayersUiState
    data class Error(val message: String) : PlayersUiState
}