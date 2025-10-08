package ir.miare.androidcodechallenge.core.domain

import ir.miare.androidcodechallenge.core.model.Player
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFollowedPlayersUseCase @Inject constructor(
    private val playerRepository: PlayerRepository
) {

    operator fun invoke(): Flow<List<Player>> =
        playerRepository.getFollowedPlayers()
}