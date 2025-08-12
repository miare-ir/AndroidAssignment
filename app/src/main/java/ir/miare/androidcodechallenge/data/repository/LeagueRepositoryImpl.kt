package ir.miare.androidcodechallenge.data.repository

import ir.miare.androidcodechallenge.data.model.LeagueData
import ir.miare.androidcodechallenge.data.remote.ApiService
import ir.miare.androidcodechallenge.domain.repository.LeagueRepository

class LeagueRepositoryImpl(val apiService: ApiService) : LeagueRepository {
    override suspend fun getLeagueData(): List<LeagueData> {
        return apiService.getLeagueData()
    }
}