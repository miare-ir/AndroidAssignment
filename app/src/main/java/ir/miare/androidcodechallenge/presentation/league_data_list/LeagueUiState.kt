package ir.miare.androidcodechallenge.presentation.league_data_list

import ir.miare.androidcodechallenge.data.model.LeagueData

data class LeagueUiState(
    val isLoading: Boolean = false,
    val leagues: List<LeagueData> = emptyList(),
    val error: String? = null
)
