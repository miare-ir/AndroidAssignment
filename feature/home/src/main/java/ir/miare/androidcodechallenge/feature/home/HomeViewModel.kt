package ir.miare.androidcodechallenge.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.miare.androidcodechallenge.core.domain.GetSortedLeagueDisplayItemsUseCase
import ir.miare.androidcodechallenge.core.model.LeagueDisplayItem
import ir.miare.androidcodechallenge.core.model.SortOption
import ir.miare.androidcodechallenge.core.network.util.ApiResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModel @Inject constructor(
    private val getSortedLeagueDisplayItemsUseCase: GetSortedLeagueDisplayItemsUseCase,
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

    val pagingDataState: Flow<PagingData<LeagueDisplayItem>> =
        homeUiState
            .flatMapLatest { state ->
                when (state) {
                    is HomeUiState.Success -> {
                        Pager(
                            config = PagingConfig(pageSize = 20),
                            pagingSourceFactory = { LeagueDisplayItemPagingSource(state.displayItems) }
                        ).flow
                    }

                    else -> flow {}
                }
            }
            .cachedIn(viewModelScope)

    fun onSortChanged(option: SortOption) {
        if (option != _sortOption.value) {
            savedStateHandle[SORT_OPTION] = option
        }
    }
}