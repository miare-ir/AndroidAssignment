package ir.miare.androidcodechallenge.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.miare.androidcodechallenge.core.domain.OrderBy
import ir.miare.androidcodechallenge.core.presentation.UiState
import ir.miare.androidcodechallenge.core.presentation.UiText
import ir.miare.androidcodechallenge.domain.usecases.GetTopScorers
import ir.miare.androidcodechallenge.presentation.mappers.toUi
import ir.miare.androidcodechallenge.presentation.models.UiPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopScorersViewModel @Inject constructor(
    private val getTopScorers: GetTopScorers
) : ViewModel() {

    private var _state = MutableStateFlow<TopScorersUiState>(TopScorersUiState())
    val state = _state
        .onStart { updateList() }
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5_000),
            initialValue = _state.value
        )

    fun onIntent(intent: TopScorersScreenIntents) {
        when (intent) {
            is TopScorersScreenIntents.UpdateTopScorers -> updateList()
            is TopScorersScreenIntents.OnOrderList -> setOrderState(intent.orderBy)
            is TopScorersScreenIntents.OnPlayerSelected -> setPlayerState(intent.selectedPlayer)
        }
    }

    private fun setPlayerState(player: UiPlayer?) {
        _state.update {
            it.copy(
                selectedPlayer = player
            )
        }
    }

    private fun setOrderState(order: OrderBy) {
        _state.update {
            it.copy(orderBy = order)
        }
    }

    private fun updateList() {
        viewModelScope.launch {
            getTopScorers(
                order = state.value.orderBy
            ).onSuccess { scorers ->
                _state.update {
                    val uiScorersList = scorers.map { it.toUi() }
                    it.copy(
                        topScorers = UiState.Success(data = uiScorersList),
                    )
                }
            }.onFailure { error ->
                val errorMessage = error.localizedMessage?.let {
                    UiText.DynamicString(it)
                } ?: UiText.StringResource(
                    resId = ir.miare.androidcodechallenge.R.string.unknown_error_message
                )
                _state.update {
                    it.copy(
                        topScorers = UiState.Failure(error = errorMessage),
                    )
                }
            }
        }
    }

}