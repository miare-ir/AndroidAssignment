package ir.miare.androidcodechallenge.domain.usecases

import ir.miare.androidcodechallenge.core.domain.OrderBy
import ir.miare.androidcodechallenge.core.domain.OrderBy.*
import ir.miare.androidcodechallenge.core.domain.Sort
import ir.miare.androidcodechallenge.domain.models.TopScorers
import ir.miare.androidcodechallenge.domain.repositories.TopScorersRepository
import javax.inject.Inject

class GetTopScorers @Inject constructor(
    private val repository: TopScorersRepository
) {
    suspend operator fun invoke(
        order: OrderBy,
    ): Result<List<TopScorers>> {
        return runCatching { repository.getTopScorers().getOrThrow().orderList(order = order) }
    }

    private fun List<TopScorers>.orderList(order: OrderBy): List<TopScorers> {
        return when (order) {
            is Name -> this.orderByName(order.sort)
            is PlayerScore -> this.orderByPlayerScore(order.sort)
            is AverageScorePerMatch -> this.orderByAverageScorePerMatch(order.sort)
            is TeamRank -> this.orderByTeamRank(order.sort)
        }
    }

    private fun List<TopScorers>.orderByName(sort: Sort): List<TopScorers> {
        return if (sort == Sort.DESCENDING) {
            this.map { it.copy(players = it.players.sortedByDescending { player -> player.name }) }
        } else {
            this.map { it.copy(players = it.players.sortedBy { player -> player.name }) }
        }
    }

    private fun List<TopScorers>.orderByPlayerScore(sort: Sort): List<TopScorers> {
        return if (sort == Sort.DESCENDING) {
            this.map { scorers ->
                scorers.copy(players = scorers.players.sortedByDescending { player -> player.totalGoal })
            }
        } else {
            this.map { scorers ->
                scorers.copy(players = scorers.players.sortedBy { player -> player.totalGoal })
            }
        }
    }

    private fun List<TopScorers>.orderByAverageScorePerMatch(sort: Sort): List<TopScorers> {
        return this.map { scorers ->
            scorers.copy(
                players = if (sort == Sort.DESCENDING) {
                    scorers.players.sortedByDescending { player ->
                        player.totalGoal / scorers.league.totalMatches.toDouble()
                    }
                } else {
                    scorers.players.sortedBy { player ->
                        player.totalGoal / scorers.league.totalMatches.toDouble()
                    }
                }
            )
        }
    }

    private fun List<TopScorers>.orderByTeamRank(sort: Sort): List<TopScorers> {
        return if (sort == Sort.DESCENDING) {
            this.map { scorers ->
                scorers.copy(players = scorers.players.sortedByDescending { player -> player.team.rank })
            }
        } else {
            this.map { scorers ->
                scorers.copy(players = scorers.players.sortedBy { player -> player.team.rank })
            }
        }
    }
}

