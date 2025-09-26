package ir.miare.androidcodechallenge.feature.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.miare.androidcodechallenge.core.data.helper.SortSettings
import ir.miare.androidcodechallenge.core.data.repository.TeamRepository
import ir.miare.androidcodechallenge.core.database.model.TeamEntity
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
class TeamsViewModel @Inject constructor(
    repository: TeamRepository,
    private val sortSettings: SortSettings,
) : ViewModel() {

    val uiState: StateFlow<TeamUiState> = repository.teams()
        .map<List<TeamEntity>, TeamUiState> { TeamUiState.Success(it) }
        .onStart { emit(TeamUiState.Loading) }
        .catch { emit(TeamUiState.Error(it.message ?: "Failed to load teams")) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TeamUiState.Loading)

    val sortMode: StateFlow<SortMode> = sortSettings.sortMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SortMode.DEFAULT)

    fun onSortSelected(mode: SortMode) {
        viewModelScope.launch { sortSettings.setSortMode(mode) }
    }
}

sealed interface TeamUiState {
    data object Loading : TeamUiState
    data class Success(val data: List<TeamEntity>) : TeamUiState
    data class Error(val message: String) : TeamUiState
}

