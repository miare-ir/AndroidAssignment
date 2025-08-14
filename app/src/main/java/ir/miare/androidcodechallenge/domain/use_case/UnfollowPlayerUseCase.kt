package ir.miare.androidcodechallenge.domain.use_case

import ir.miare.androidcodechallenge.domain.repository.FollowedPlayerRepository
import javax.inject.Inject

class UnfollowPlayerUseCase @Inject constructor(
    private val repository: FollowedPlayerRepository
) {
    suspend operator fun invoke(playerName: String) {
        repository.unfollow(playerName)
    }
}