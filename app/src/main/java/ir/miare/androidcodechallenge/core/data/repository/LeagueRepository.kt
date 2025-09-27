package ir.miare.androidcodechallenge.core.data.repository

import ir.miare.androidcodechallenge.core.model.LeagueModel
import kotlinx.coroutines.flow.Flow

interface LeagueRepository {
    fun getLeagues(): Flow<List<LeagueModel>>
}