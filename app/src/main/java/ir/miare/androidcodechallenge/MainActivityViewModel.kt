package ir.miare.androidcodechallenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
) : ViewModel() {
    val uiState: StateFlow<UiState> = makeDelayForSplash().map {
        UiState.Success
    }.stateIn(
        scope = viewModelScope,
        initialValue = UiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000),
    )

    fun makeDelayForSplash() = flow {
        delay(5000)
        emit(Unit)
    }
}

sealed interface UiState {
    data object Loading : UiState
    data object Success : UiState
}