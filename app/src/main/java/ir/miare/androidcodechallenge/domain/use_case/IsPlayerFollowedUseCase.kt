package ir.miare.androidcodechallenge.domain.use_case

import ir.miare.androidcodechallenge.domain.repository.FollowedPlayerRepository
import javax.inject.Inject

class IsPlayerFollowedUseCase @Inject constructor(
    private val repository: FollowedPlayerRepository
) {
    suspend operator fun invoke(playerName: String): Boolean {
        return repository.isFollowed(playerName)
    }
}
