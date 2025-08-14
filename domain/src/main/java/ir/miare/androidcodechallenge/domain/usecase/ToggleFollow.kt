package ir.miare.androidcodechallenge.domain.usecase

import ir.miare.androidcodechallenge.domain.model.PlayerId
import ir.miare.androidcodechallenge.domain.repo.FollowRepository
import javax.inject.Inject

class ToggleFollow @Inject constructor(private val repository: FollowRepository) {
    suspend operator fun invoke(playerId: PlayerId) = repository.toggleFollow(playerId)
}
