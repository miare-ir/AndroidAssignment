package ir.miare.androidcodechallenge.domain.use_case

import ir.miare.androidcodechallenge.data.db.FollowedPlayerEntity
import ir.miare.androidcodechallenge.domain.repository.FollowedPlayerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFollowedPlayersUseCase @Inject constructor(
    private val repository: FollowedPlayerRepository
) {
    operator fun invoke(): Flow<List<FollowedPlayerEntity>> {
        return repository.getFollowedPlayers()
    }
}
