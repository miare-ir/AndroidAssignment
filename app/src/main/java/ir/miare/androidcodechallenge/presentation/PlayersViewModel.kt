@file:OptIn(ExperimentalCoroutinesApi::class)

package ir.miare.androidcodechallenge.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.miare.androidcodechallenge.domain.entity.Player
import ir.miare.androidcodechallenge.domain.usecase.GetPlayersPagedUseCase
import ir.miare.androidcodechallenge.domain.usecase.ObserveFollowedUseCase
import ir.miare.androidcodechallenge.domain.usecase.SortMode
import ir.miare.androidcodechallenge.domain.usecase.ToggleFollowUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(
    private val getPlayersPaged: GetPlayersPagedUseCase,
    observeFollowed: ObserveFollowedUseCase,
    private val toggleFollow: ToggleFollowUseCase
) : ViewModel() {

    private val sortModeState: MutableStateFlow<SortMode> = MutableStateFlow(SortMode.ALPHABETICAL)
    private val pageSize: MutableStateFlow<Int> = MutableStateFlow(10)
    private val showOnlyFollowedState: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val followed: StateFlow<Set<String>> = observeFollowed()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptySet())

    val sortMode: StateFlow<SortMode> = sortModeState

    val pagedPlayers: StateFlow<PagingData<Player>> =
        combine(sortModeState, pageSize, showOnlyFollowedState, followed) { sort, size, showOnlyFollowed, followedSet ->
            if (showOnlyFollowed) {
                getPlayersPaged.invoke(size, sort, followedSet)
            } else {
                getPlayersPaged.invoke(size, sort)
            }
        }
            .flatMapLatest { flow ->
                flow
            }
            .cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.Eagerly, PagingData.empty())

    fun setSortMode(mode: SortMode) {
        sortModeState.value = mode
    }

    fun setShowOnlyFollowed(showOnlyFollowed: Boolean) {
        showOnlyFollowedState.value = showOnlyFollowed
    }

    fun onToggleFollow(playerName: String) {
        viewModelScope.launch {
            toggleFollow(playerName)
        }
    }
}




