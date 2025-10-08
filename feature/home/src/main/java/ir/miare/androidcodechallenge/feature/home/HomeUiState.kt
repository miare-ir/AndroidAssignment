package ir.miare.androidcodechallenge.feature.home

import ir.miare.androidcodechallenge.core.model.LeagueDisplayItem

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val displayItems: List<LeagueDisplayItem>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}