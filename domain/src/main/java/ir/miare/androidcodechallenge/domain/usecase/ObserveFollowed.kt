package ir.miare.androidcodechallenge.domain.usecase

import ir.miare.androidcodechallenge.domain.model.PlayerId
import ir.miare.androidcodechallenge.domain.repo.FollowRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveFollowed @Inject constructor(private val repository: FollowRepository) {
    operator fun invoke(): Flow<Set<PlayerId>> = repository.observeFollowed()
}
