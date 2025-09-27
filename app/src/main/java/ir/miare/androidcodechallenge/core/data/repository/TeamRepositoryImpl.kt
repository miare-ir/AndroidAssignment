package ir.miare.androidcodechallenge.core.data.repository

import ir.miare.androidcodechallenge.core.database.dao.TeamDao
import ir.miare.androidcodechallenge.core.database.model.TeamEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class TeamRepositoryImpl @Inject constructor(
    private val teamDao: TeamDao,
    private val sortSettings: SortPreferencesRepository,
) : TeamRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun teams(): Flow<List<TeamEntity>> {
        return sortSettings.sortMode
            .flatMapLatest { mode ->
                teamDao.getTeamsSorted(mode.storageKey)
            }
    }
}