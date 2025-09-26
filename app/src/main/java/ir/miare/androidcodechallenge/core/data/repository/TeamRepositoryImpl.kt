package ir.miare.androidcodechallenge.core.data.repository

import ir.miare.androidcodechallenge.core.data.helper.SortSettings
import ir.miare.androidcodechallenge.core.database.dao.TeamDao
import ir.miare.androidcodechallenge.core.database.model.TeamEntity
import ir.miare.androidcodechallenge.core.model.SortMode
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class TeamRepositoryImpl @Inject constructor(
    private val teamDao: TeamDao,
    private val sortSettings: SortSettings,
) : TeamRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun teams(): Flow<List<TeamEntity>> {
        return sortSettings.sortMode
            .flatMapLatest { mode ->
                when (mode) {
                    SortMode.TEAM_RANK -> teamDao.getTeamsSortedByRank()
                    else -> teamDao.getAllTeams()
                }
            }
    }
}