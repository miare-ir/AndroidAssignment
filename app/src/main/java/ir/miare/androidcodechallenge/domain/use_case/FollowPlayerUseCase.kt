package ir.miare.androidcodechallenge.domain.use_case

import ir.miare.androidcodechallenge.data.model.League
import ir.miare.androidcodechallenge.data.model.Player
import ir.miare.androidcodechallenge.domain.repository.FollowedPlayerRepository
import javax.inject.Inject

class FollowPlayerUseCase @Inject constructor(
    private val repository: FollowedPlayerRepository
) {
    suspend operator fun invoke(player: Player, league: League) {
        repository.follow(player, league)
    }
}
