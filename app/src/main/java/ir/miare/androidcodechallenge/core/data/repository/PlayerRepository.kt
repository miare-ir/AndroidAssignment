package ir.miare.androidcodechallenge.core.data.repository

import ir.miare.androidcodechallenge.core.model.PlayerWithDetails
import ir.miare.androidcodechallenge.core.model.SortMode
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {
    val sortMode: Flow<SortMode>

    suspend fun ensureSeeded()
    suspend fun setSortMode(mode: SortMode)
    suspend fun setPlayerFollowed(playerId: String, isFollowed: Boolean)

    fun players(pageSize: Int, offset: Int): Flow<List<PlayerWithDetails>>
    fun followedPlayers(): Flow<List<PlayerWithDetails>>
}
