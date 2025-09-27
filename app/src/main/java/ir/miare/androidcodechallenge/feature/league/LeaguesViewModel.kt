package ir.miare.androidcodechallenge.feature.league

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.miare.androidcodechallenge.core.domain.usecases.GetLeaguesUseCase
import ir.miare.androidcodechallenge.core.domain.usecases.GetSortModeUseCase
import ir.miare.androidcodechallenge.core.domain.usecases.SetSortModeUseCase
import ir.miare.androidcodechallenge.core.model.LeagueModel
import ir.miare.androidcodechallenge.core.model.SortMode
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaguesViewModel @Inject constructor(
    private val setSortMode: SetSortModeUseCase,
    getLeagues: GetLeaguesUseCase,
    getSortMode: GetSortModeUseCase,
) : ViewModel() {

    val uiState: StateFlow<LeagueUiState> = getLeagues()
        .map<List<LeagueModel>, LeagueUiState> { LeagueUiState.Success(it) }
        .onStart { emit(LeagueUiState.Loading) }
        .catch { emit(LeagueUiState.Error(it.message ?: "Failed to load leagues")) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LeagueUiState.Loading)

    val sortMode: StateFlow<SortMode> = getSortMode()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SortMode.DEFAULT)

    fun onSortSelected(mode: SortMode) {
        viewModelScope.launch { setSortMode(mode) }
    }
}

sealed interface LeagueUiState {
    data object Loading : LeagueUiState
    data class Success(val data: List<LeagueModel>) : LeagueUiState
    data class Error(val message: String) : LeagueUiState
}
