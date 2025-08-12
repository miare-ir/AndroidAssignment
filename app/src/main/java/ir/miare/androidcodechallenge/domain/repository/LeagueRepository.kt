package ir.miare.androidcodechallenge.domain.repository

import ir.miare.androidcodechallenge.data.model.LeagueData

interface LeagueRepository {
    suspend fun getLeagueData(): List<LeagueData>
}