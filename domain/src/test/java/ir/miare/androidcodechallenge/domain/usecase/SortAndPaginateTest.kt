package ir.miare.androidcodechallenge.domain.usecase

import ir.miare.androidcodechallenge.domain.model.PlayerId
import ir.miare.androidcodechallenge.domain.model.PlayerWithContext
import ir.miare.androidcodechallenge.domain.model.SortMode
import org.junit.Assert.assertEquals
import org.junit.Test

class SortAndPaginateTest {

    private fun players(): List<PlayerWithContext> = listOf(
        PlayerWithContext(PlayerId("1"), "A", 10, "Team1", 2, "League1", "C1", 2, 38),
        PlayerWithContext(PlayerId("2"), "B", 20, "Team2", 1, "League2", "C2", 1, 38),
        PlayerWithContext(PlayerId("3"), "C", 5, "Team3", 3, "League1", "C1", 2, 38)
    )

    @Test
    fun mostGoals_then_paginate() {
        val sorted = SortPlayers.sort(players(), SortMode.MOST_GOALS)
        val page = PaginatePlayers.paginate(sorted)
        assertEquals(listOf("B", "A", "C"), page.map { it.name })
    }
}


