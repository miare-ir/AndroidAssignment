package ir.miare.androidcodechallenge.core.domain

import ir.miare.androidcodechallenge.core.model.Player
import javax.inject.Inject

class FollowPlayerUseCase @Inject constructor(
    private val playerRepository: PlayerRepository
) {
    suspend operator fun invoke(player: Player) {
        playerRepository.followPlayer(player)
    }
}