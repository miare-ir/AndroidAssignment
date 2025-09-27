package ir.miare.androidcodechallenge.core.data.repository

import ir.miare.androidcodechallenge.core.database.dao.TeamDao
import ir.miare.androidcodechallenge.core.database.model.asExternalModel
import ir.miare.androidcodechallenge.core.model.TeamModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TeamRepositoryImpl @Inject constructor(
    private val teamDao: TeamDao,
) : TeamRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getTeams(sortKey: String): Flow<List<TeamModel>> =
        teamDao.getTeamsSorted(sortKey).map {
            it.map { entity -> entity.asExternalModel() }
        }
}