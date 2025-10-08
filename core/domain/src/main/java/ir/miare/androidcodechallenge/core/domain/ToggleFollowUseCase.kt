package ir.miare.androidcodechallenge.core.domain

import ir.miare.androidcodechallenge.core.model.Player
import ir.miare.androidcodechallenge.core.model.stableKey
import javax.inject.Inject

class ToggleFollowUseCase @Inject constructor(
    private val playerRepository: PlayerRepository
) {
    suspend operator fun invoke(player: Player) {
        val key = player.stableKey()
        if (playerRepository.isFollowed(key)) playerRepository.unFollowPlayer(player) else playerRepository.followPlayer(player)
    }
}