package ir.miare.androidcodechallenge.domain.repository

import kotlinx.coroutines.flow.Flow

interface FollowRepository {
    fun observeFollowedPlayers(): Flow<Set<String>>
    suspend fun toggleFollow(playerName: String)
}
