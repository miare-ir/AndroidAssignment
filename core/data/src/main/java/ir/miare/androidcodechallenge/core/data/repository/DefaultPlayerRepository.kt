package ir.miare.androidcodechallenge.core.data.repository

import ir.miare.androidcodechallenge.core.data.model.asEntity
import ir.miare.androidcodechallenge.core.database.dao.FollowedPlayerDao
import ir.miare.androidcodechallenge.core.database.entity.FollowedPlayerEntity
import ir.miare.androidcodechallenge.core.database.entity.asPlayer
import ir.miare.androidcodechallenge.core.domain.PlayerRepository
import ir.miare.androidcodechallenge.core.model.Player
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultPlayerRepository @Inject constructor(
    private val followedPlayerDao: FollowedPlayerDao
) : PlayerRepository {
    override suspend fun followPlayer(player: Player) {
        followedPlayerDao.insert(player.asEntity())
    }

    override suspend fun unFollowPlayer(player: Player) {
        followedPlayerDao.delete(player.asEntity())
    }

    override fun getFollowedPlayers(): Flow<List<Player>> =
        followedPlayerDao.getAllPlayers().map{it.map(FollowedPlayerEntity::asPlayer)}
}