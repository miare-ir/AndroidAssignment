package ir.miare.androidcodechallenge.data.repository

import ir.miare.androidcodechallenge.data.local.FollowStore
import ir.miare.androidcodechallenge.domain.repository.FollowRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FollowRepositoryImpl @Inject constructor(
    private val followStore: FollowStore
) : FollowRepository {
    
    override fun observeFollowedPlayers(): Flow<Set<String>> = followStore.followedIds
    
    override suspend fun toggleFollow(playerName: String) = followStore.toggleFollow(playerName)
}
