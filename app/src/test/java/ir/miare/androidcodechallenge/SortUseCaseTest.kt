package ir.miare.androidcodechallenge

import ir.miare.androidcodechallenge.domain.model.PlayerId
import ir.miare.androidcodechallenge.domain.model.PlayerWithContext
import ir.miare.androidcodechallenge.domain.model.SortMode
import ir.miare.androidcodechallenge.domain.usecase.SortPlayers
import org.junit.Assert.assertEquals
import org.junit.Test

class SortUseCaseTest {

    private fun sample(): List<PlayerWithContext> = listOf(
        PlayerWithContext(PlayerId("1"), "A", 10, "Team1", 2, "League1", "C1", 2, 38),
        PlayerWithContext(PlayerId("2"), "B", 20, "Team2", 1, "League2", "C2", 1, 38),
        PlayerWithContext(PlayerId("3"), "C", 5, "Team3", 3, "League1", "C1", 2, 38)
    )

    @Test
    fun sort_mostGoals_desc() {
        val sorted = SortPlayers.sort(sample(), SortMode.MOST_GOALS)
        assertEquals(listOf("B", "A", "C"), sorted.map { it.name })
    }

    @Test
    fun sort_teamAndLeagueRank() {
        val sorted = SortPlayers.sort(sample(), SortMode.TEAM_AND_LEAGUE_RANK)
        assertEquals(listOf("B", "A", "C"), sorted.map { it.name })
    }
}


