package ir.miare.androidcodechallenge.presentation

import ir.miare.androidcodechallenge.core.domain.OrderBy
import ir.miare.androidcodechallenge.core.domain.Sort

sealed interface TopScorersScreenIntents {
    object UpdateTopScorers : TopScorersScreenIntents
    data class OrderList(val orderBy: OrderBy) : TopScorersScreenIntents
    data class SortList(val sortType: Sort) : TopScorersScreenIntents
}