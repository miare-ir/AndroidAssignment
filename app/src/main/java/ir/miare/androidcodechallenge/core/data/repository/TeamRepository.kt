package ir.miare.androidcodechallenge.core.data.repository

import ir.miare.androidcodechallenge.core.model.TeamModel
import kotlinx.coroutines.flow.Flow

interface TeamRepository {
    fun getTeams(sortKey: String): Flow<List<TeamModel>>
}