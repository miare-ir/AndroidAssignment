package ir.miare.androidcodechallenge.domain.usecase

import ir.miare.androidcodechallenge.domain.repository.FollowRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveFollowedUseCase @Inject constructor(
    private val repository: FollowRepository
) {
    operator fun invoke(): Flow<Set<String>> = repository.observeFollowedPlayers()
}

class ToggleFollowUseCase @Inject constructor(
    private val repository: FollowRepository
) {
    suspend operator fun invoke(playerName: String) = repository.toggleFollow(playerName)
}


