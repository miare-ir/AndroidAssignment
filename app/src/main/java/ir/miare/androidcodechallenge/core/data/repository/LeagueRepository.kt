package ir.miare.androidcodechallenge.core.data.repository

import ir.miare.androidcodechallenge.core.database.model.LeagueEntity
import kotlinx.coroutines.flow.Flow

interface LeagueRepository {
    fun getLeagues(): Flow<List<LeagueEntity>>
}