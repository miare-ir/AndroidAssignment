package ir.miare.androidcodechallenge.core.data.repository

import ir.miare.androidcodechallenge.core.database.dao.LeagueDao
import ir.miare.androidcodechallenge.core.database.model.LeagueEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class LeagueRepositoryImpl @Inject constructor(
    private val leagueDao: LeagueDao,
    private val sortSettings: SortPreferencesRepository,
) : LeagueRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun leagues(): Flow<List<LeagueEntity>> {
        return sortSettings.sortMode
            .flatMapLatest { mode ->
                leagueDao.getLeaguesSorted(mode.storageKey)
            }
    }
}
