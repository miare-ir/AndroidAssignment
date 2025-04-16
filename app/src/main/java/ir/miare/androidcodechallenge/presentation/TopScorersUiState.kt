package ir.miare.androidcodechallenge.presentation

import ir.miare.androidcodechallenge.core.domain.OrderBy
import ir.miare.androidcodechallenge.core.presentation.UiState
import ir.miare.androidcodechallenge.presentation.models.UiPlayer
import ir.miare.androidcodechallenge.presentation.models.UiTopScorers

data class TopScorersUiState(
    val topScorers: UiState<List<UiTopScorers>> = UiState.Loading(),
    val orderBy: OrderBy = OrderBy.Name(),
    val selectedPlayer: UiPlayer? = null
)
