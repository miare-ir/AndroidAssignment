package ir.miare.androidcodechallenge.domain.repo

import ir.miare.androidcodechallenge.domain.model.PlayerId
import kotlinx.coroutines.flow.Flow

interface FollowRepository {
    fun observeFollowed(): Flow<Set<PlayerId>>
    suspend fun toggleFollow(playerId: PlayerId)
}
