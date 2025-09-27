package ir.miare.androidcodechallenge.core.domain.usecases

import ir.miare.androidcodechallenge.core.data.repository.PlayerRepository
import javax.inject.Inject

class SetPlayerFollowedUseCase @Inject constructor(
    private val repository: PlayerRepository
) {
    suspend operator fun invoke(playerId: String, isFollowed: Boolean) =
        repository.setPlayerFollowed(playerId, isFollowed)
}