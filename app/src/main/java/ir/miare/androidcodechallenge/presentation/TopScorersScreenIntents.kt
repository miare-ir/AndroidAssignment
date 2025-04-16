package ir.miare.androidcodechallenge.presentation

import ir.miare.androidcodechallenge.core.domain.OrderBy
import ir.miare.androidcodechallenge.presentation.models.UiPlayer

sealed interface TopScorersScreenIntents {
    object UpdateTopScorers : TopScorersScreenIntents
    data class OnOrderList(val orderBy: OrderBy) : TopScorersScreenIntents
    data class OnPlayerSelected(val selectedPlayer: UiPlayer? = null) : TopScorersScreenIntents
}