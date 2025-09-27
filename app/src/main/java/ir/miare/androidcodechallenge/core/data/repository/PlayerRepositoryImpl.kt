package ir.miare.androidcodechallenge.core.data.repository

import ir.miare.androidcodechallenge.core.database.dao.PlayerDao
import ir.miare.androidcodechallenge.core.model.SortMode
import ir.miare.androidcodechallenge.core.model.asExternalModel
import ir.miare.androidcodechallenge.core.model.networkmodel.PlayerModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val playerDao: PlayerDao,
    private val sortSettings: SortPreferencesRepository,
) : PlayerRepository {

    override val sortMode: Flow<SortMode> = sortSettings.sortMode
    override suspend fun setSortMode(mode: SortMode) = sortSettings.setSortMode(mode)

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getPlayers(pageSize: Int, offset: Int, sortKey: String): Flow<List<PlayerModel>> =
        playerDao.getPlayersPagedSorted(pageSize, offset, sortKey)
            .map { it.map { dto -> dto.asExternalModel() } }


    override fun followedPlayers(): Flow<List<PlayerModel>> =
        playerDao.getFollowedPlayers().map { it.map { dto -> dto.asExternalModel() } }

    override suspend fun setPlayerFollowed(playerId: String, isFollowed: Boolean) =
        playerDao.updatePlayerFollowStatus(playerId, isFollowed)
}
