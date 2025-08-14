package ir.miare.androidcodechallenge.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.miare.androidcodechallenge.domain.model.PlayerId
import ir.miare.androidcodechallenge.domain.model.PlayerWithContext
import ir.miare.androidcodechallenge.domain.model.SortMode
import ir.miare.androidcodechallenge.domain.usecase.GetAllPlayers
import ir.miare.androidcodechallenge.domain.usecase.ObserveFollowed
import ir.miare.androidcodechallenge.domain.usecase.PaginatePlayers
import ir.miare.androidcodechallenge.domain.usecase.SortPlayers
import ir.miare.androidcodechallenge.domain.usecase.ToggleFollow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PlayersUiState(
    val isLoading: Boolean = true,
    val sortMode: SortMode = SortMode.NONE,
    val isLoadingNextPage: Boolean = false,
    val items: List<PlayerItemUi> = emptyList()
)

data class PlayerItemUi(
    val id: PlayerId,
    val name: String,
    val teamName: String,
    val leagueName: String,
    val totalGoals: Int,
    val teamRank: Int,
    val leagueRank: Int,
    val isFollowed: Boolean
)

@HiltViewModel
class PlayersViewModel @Inject constructor(
    private val getAllPlayers: GetAllPlayers,
    observeFollowed: ObserveFollowed,
    private val toggleFollow: ToggleFollow
) : ViewModel() {
    private lateinit var data: List<PlayerWithContext>
    private val allPlayersFlow = MutableStateFlow<List<PlayerWithContext>>(emptyList())
    private val followedFlow = observeFollowed().stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.Eagerly,
        initialValue = emptySet()
    )

    private val _uiState = MutableStateFlow(PlayersUiState())
    val uiState: StateFlow<PlayersUiState> = _uiState.asStateFlow()
    val followedUi: StateFlow<List<PlayerItemUi>> = combine(
        allPlayersFlow, followedFlow
    ) { playerList: List<PlayerWithContext>, followedSet: Set<PlayerId> ->
        playerList.filter { player -> followedSet.contains(player.id) }
            .map { it.toUi(true) }
    }.stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    init {
        viewModelScope.launch {
            data = getAllPlayers()
            allPlayersFlow.value = SortPlayers.sort(data, uiState.value.sortMode)
        }

        viewModelScope.launch {
            allPlayersFlow.collect { playerList ->
                PaginatePlayers.reset()
                val items = PaginatePlayers.paginate(playerList)
                    .map { it.toUi(followedFlow.value.contains(it.id)) }
                _uiState.value = uiState.value.copy(
                    isLoading = false,
                    items = items,
                )
            }
        }
    }

    fun setSortMode(mode: SortMode) {
        _uiState.value = uiState.value.copy(sortMode = mode)
        allPlayersFlow.value = SortPlayers.sort(data, mode)
    }

    fun loadNextPage() = viewModelScope.launch {
        if (uiState.value.items.size >= data.size) return@launch
        _uiState.value = uiState.value.copy(
            isLoadingNextPage = true
        )
        delay(1000)
        _uiState.value = uiState.value.copy(
            items = PaginatePlayers.paginate(allPlayersFlow.value)
                .map { it.toUi(followedFlow.value.contains(it.id)) },
            isLoadingNextPage = false
        )
    }

    fun onToggleFollow(id: PlayerId) {
        viewModelScope.launch { toggleFollow(id) }
    }
}

private fun PlayerWithContext.toUi(isFollowed: Boolean): PlayerItemUi = PlayerItemUi(
    id = this.id,
    name = this.name,
    teamName = this.teamName,
    leagueName = this.leagueName,
    totalGoals = this.totalGoals,
    teamRank = this.teamRank,
    leagueRank = this.leagueRank,
    isFollowed = isFollowed
)


