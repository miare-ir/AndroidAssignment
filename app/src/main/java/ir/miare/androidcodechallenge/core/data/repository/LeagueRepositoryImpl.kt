package ir.miare.androidcodechallenge.core.data.repository

import ir.miare.androidcodechallenge.core.data.helper.SortSettings
import ir.miare.androidcodechallenge.core.database.dao.LeagueDao
import ir.miare.androidcodechallenge.core.database.model.LeagueEntity
import ir.miare.androidcodechallenge.core.model.SortMode
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class LeagueRepositoryImpl @Inject constructor(
    private val leagueDao: LeagueDao,
    private val sortSettings: SortSettings,
) : LeagueRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun leagues(): Flow<List<LeagueEntity>> {
        return sortSettings.sortMode
            .flatMapLatest { mode ->
                when (mode) {
                    SortMode.LEAGUE_GOAL_AVG -> leagueDao.getLeaguesSortedByGoalAverage()
                    SortMode.LEAGUE_RANK -> leagueDao.getLeaguesSortedByRank()
                    else -> leagueDao.getAllLeagues()
                }
            }
    }
}
