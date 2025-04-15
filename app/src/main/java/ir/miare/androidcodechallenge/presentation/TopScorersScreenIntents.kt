package ir.miare.androidcodechallenge.presentation

import ir.miare.androidcodechallenge.core.domain.OrderBy

sealed interface TopScorersScreenIntents {
    object UpdateTopScorers : TopScorersScreenIntents
    data class OnOrderList(val orderBy: OrderBy) : TopScorersScreenIntents
}