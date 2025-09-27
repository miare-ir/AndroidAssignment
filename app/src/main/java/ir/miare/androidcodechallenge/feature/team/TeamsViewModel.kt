package ir.miare.androidcodechallenge.feature.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.miare.androidcodechallenge.core.domain.usecases.GetSortModeUseCase
import ir.miare.androidcodechallenge.core.domain.usecases.GetTeamsUseCase
import ir.miare.androidcodechallenge.core.domain.usecases.SetSortModeUseCase
import ir.miare.androidcodechallenge.core.model.SortMode
import ir.miare.androidcodechallenge.core.model.TeamModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamsViewModel @Inject constructor(
    private val setSortMode: SetSortModeUseCase,
    getTeamsUseCase: GetTeamsUseCase,
    getSortMode: GetSortModeUseCase,

    ) : ViewModel() {

    val sortMode: StateFlow<SortMode> = getSortMode()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SortMode.DEFAULT)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<TeamUiState> = sortMode
        .flatMapLatest { mode -> getTeamsUseCase(mode.storageKey) }
        .map<List<TeamModel>, TeamUiState> { TeamUiState.Success(it) }
        .onStart { emit(TeamUiState.Loading) }
        .catch { emit(TeamUiState.Error(it.message ?: "Failed to load teams")) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TeamUiState.Loading)

    fun onSortSelected(mode: SortMode) {
        viewModelScope.launch { setSortMode(mode) }
    }
}

sealed interface TeamUiState {
    data object Loading : TeamUiState
    data class Success(val data: List<TeamModel>) : TeamUiState
    data class Error(val message: String) : TeamUiState
}

