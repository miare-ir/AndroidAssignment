package ir.miare.androidcodechallenge.core.data.repository

import ir.miare.androidcodechallenge.core.database.dao.LeagueDao
import ir.miare.androidcodechallenge.core.database.model.asExternalModel
import ir.miare.androidcodechallenge.core.model.LeagueModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LeagueRepositoryImpl @Inject constructor(
    private val leagueDao: LeagueDao,
) : LeagueRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getLeagues(sortKey: String): Flow<List<LeagueModel>> =
        leagueDao.getLeaguesSorted(sortKey).map { it.map { entity -> entity.asExternalModel() } }
}
