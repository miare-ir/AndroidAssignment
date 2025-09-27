package ir.miare.androidcodechallenge.core.data.repository

import ir.miare.androidcodechallenge.core.database.model.TeamEntity
import kotlinx.coroutines.flow.Flow

interface TeamRepository {
    fun getTeams(): Flow<List<TeamEntity>>
}