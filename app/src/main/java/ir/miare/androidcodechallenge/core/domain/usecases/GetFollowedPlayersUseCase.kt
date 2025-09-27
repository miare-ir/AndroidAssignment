package ir.miare.androidcodechallenge.core.domain.usecases

import ir.miare.androidcodechallenge.core.data.repository.PlayerRepository
import ir.miare.androidcodechallenge.core.model.PlayerWithDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFollowedPlayersUseCase @Inject constructor(
    private val repository: PlayerRepository
) {
    operator fun invoke(): Flow<List<PlayerWithDetails>> = repository.followedPlayers()
}


