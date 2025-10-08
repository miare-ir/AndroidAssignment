package ir.miare.androidcodechallenge.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.miare.androidcodechallenge.core.domain.GetSortedLeagueDisplayItemsUseCase
import ir.miare.androidcodechallenge.core.domain.ObserveFollowedSet
import ir.miare.androidcodechallenge.core.domain.ToggleFollow
import ir.miare.androidcodechallenge.core.domain.UnFollowPlayerUseCase
import ir.miare.androidcodechallenge.core.model.LeagueDisplayItem
import ir.miare.androidcodechallenge.core.model.Player
import ir.miare.androidcodechallenge.core.model.SortOption
import ir.miare.androidcodechallenge.core.model.stableKey
import ir.miare.androidcodechallenge.core.network.util.ApiResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSortedLeagueDisplayItemsUseCase: GetSortedLeagueDisplayItemsUseCase,
    private val unFollowPlayerUseCase: UnFollowPlayerUseCase,
    private val observeFollowedSet: ObserveFollowedSet,
    private val toggleFollow: ToggleFollow,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    companion object {
        private const val SORT_OPTION = "sort_option"
    }

    private val _sortOption =
        savedStateHandle.getStateFlow(key = SORT_OPTION, initialValue = SortOption.NONE)
    val sortOption: StateFlow<SortOption> = _sortOption

    val homeUiState: StateFlow<HomeUiState> =
        _sortOption
            .flatMapLatest { option ->
                getSortedLeagueDisplayItemsUseCase(option).map { result ->
                    when (result) {
                        is ApiResult.Success ->
                            HomeUiState.Success(result.data)

                        is ApiResult.Error ->
                            HomeUiState.Error(result.throwable.message ?: "error happened")
                    }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = HomeUiState.Loading
            )

    private val basePaging: Flow<PagingData<LeagueDisplayItem>> =
        homeUiState
            .flatMapLatest { state ->
                when (state) {
                    is HomeUiState.Success -> {
                        Pager(
                            config = PagingConfig(
                                pageSize = 20,
                                enablePlaceholders = true,
                                prefetchDistance = 2),
                            pagingSourceFactory = {
                                LeagueDisplayItemPagingSource(
                                      items = state.displayItems
                                )
                            }
                        ).flow
                    }

                    else -> flowOf(PagingData.empty())
                }
            }
            .cachedIn(viewModelScope)

    private val followedKeys: StateFlow<Set<String>> =
        observeFollowedSet()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptySet())


    val pagingUi: Flow<PagingData<LeagueDisplayItem>> =
        combine(basePaging, followedKeys) { paging, keys ->
            paging.map { item ->
                when (item) {
                    is LeagueDisplayItem.Header -> {
                        item
                    }
                    is LeagueDisplayItem.PlayerItem -> {
                        item.player.isFollowed = item.player.stableKey() in keys
                        item
                    }
                }
            }
        }.cachedIn(viewModelScope)

    fun onSortChanged(option: SortOption) {
        if (option != _sortOption.value) {
            savedStateHandle[SORT_OPTION] = option
        }
    }

    fun onFollowClick(player: Player) {
        viewModelScope.launch {
            try {
                toggleFollow(player)
            } catch (t: Throwable) {
            }
        }
    }
}