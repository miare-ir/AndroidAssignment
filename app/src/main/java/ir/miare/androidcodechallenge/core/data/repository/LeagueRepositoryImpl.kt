package ir.miare.androidcodechallenge.core.data.repository

import ir.miare.androidcodechallenge.core.database.dao.LeagueDao
import ir.miare.androidcodechallenge.core.database.model.asExternalModel
import ir.miare.androidcodechallenge.core.model.LeagueModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LeagueRepositoryImpl @Inject constructor(
    private val leagueDao: LeagueDao,
    private val sortSettings: SortPreferencesRepository,
) : LeagueRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getLeagues(): Flow<List<LeagueModel>> =
        sortSettings.sortMode
            .flatMapLatest { mode -> leagueDao.getLeaguesSorted(mode.storageKey) }
            .map { it.map { entity -> entity.asExternalModel() } }
}
