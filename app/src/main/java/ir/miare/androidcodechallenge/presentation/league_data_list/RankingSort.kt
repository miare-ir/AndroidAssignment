package ir.miare.androidcodechallenge.presentation.league_data_list

sealed class RankingSort {
    object None : RankingSort()
    object ByTeam : RankingSort()
    object ByLeagueRanking : RankingSort()
    object ByMostGoals : RankingSort()
    object ByAverageGoalsPerMatch : RankingSort()

    fun displayName(): String = when (this) {
        None -> "Default"
        ByTeam -> "Team Name"
        ByLeagueRanking -> "League Ranking"
        ByMostGoals -> "Most Goals"
        ByAverageGoalsPerMatch -> "Average Goals per Match"
    }

    companion object {
        fun values(): List<RankingSort> = listOf(
            None,
            ByTeam,
            ByLeagueRanking,
            ByMostGoals,
            ByAverageGoalsPerMatch
        )
    }
}
