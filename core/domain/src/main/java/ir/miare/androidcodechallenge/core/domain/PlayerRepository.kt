package ir.miare.androidcodechallenge.core.domain

import ir.miare.androidcodechallenge.core.model.Player
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {
    suspend fun followPlayer(player: Player)
    suspend fun unFollowPlayer(player: Player)
    fun getFollowedPlayers(): Flow<List<Player>>
    suspend fun isFollowed(key: String): Boolean
    fun observeFollowed(): Flow<Set<String>>
}