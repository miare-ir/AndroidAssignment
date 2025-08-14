package ir.miare.androidcodechallenge.domain.usecase

import ir.miare.androidcodechallenge.domain.model.PlayerWithContext
import ir.miare.androidcodechallenge.domain.model.SortMode

object SortPlayers {
    fun sort(players: List<PlayerWithContext>, mode: SortMode): List<PlayerWithContext> = when (mode) {
        SortMode.NONE -> players
        SortMode.TEAM_AND_LEAGUE_RANK -> players.sortedWith(
            compareBy<PlayerWithContext> { it.teamRank }
                .thenBy { it.leagueRank }
                .thenByDescending { it.totalGoals }
                .thenBy { it.name }
        )
        SortMode.MOST_GOALS -> players.sortedWith(
            compareByDescending<PlayerWithContext> { it.totalGoals }
                .thenBy { it.teamRank }
                .thenBy { it.leagueRank }
                .thenBy { it.name }
        )
        SortMode.LEAGUE_AVG_GOALS -> {
            val leagueAvg = players.groupBy { it.leagueName to it.leagueTotalMatches }.mapValues { (_, list) ->
                val total = list.sumOf { it.totalGoals }
                val matches = list.first().leagueTotalMatches.coerceAtLeast(1)
                total.toDouble() / matches
            }
            players.sortedWith(
                compareByDescending<PlayerWithContext> { leagueAvg[it.leagueName to it.leagueTotalMatches] ?: 0.0 }
                    .thenByDescending { it.totalGoals }
                    .thenBy { it.teamRank }
                    .thenBy { it.leagueRank }
                    .thenBy { it.name }
            )
        }
    }
}
