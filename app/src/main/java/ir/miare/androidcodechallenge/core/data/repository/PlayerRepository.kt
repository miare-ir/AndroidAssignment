package ir.miare.androidcodechallenge.core.data.repository

import ir.miare.androidcodechallenge.core.model.SortMode
import ir.miare.androidcodechallenge.core.model.networkmodel.PlayerModel
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {
    val sortMode: Flow<SortMode>

    suspend fun setSortMode(mode: SortMode)
    suspend fun setPlayerFollowed(playerId: String, isFollowed: Boolean)

    fun players(pageSize: Int, offset: Int): Flow<List<PlayerModel>>
    fun followedPlayers(): Flow<List<PlayerModel>>
}
