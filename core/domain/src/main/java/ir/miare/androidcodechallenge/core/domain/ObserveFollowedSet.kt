package ir.miare.androidcodechallenge.core.domain

import javax.inject.Inject

class ObserveFollowedSet @Inject constructor(
    private val playerRepository: PlayerRepository
) {
    operator fun invoke() = playerRepository.observeFollowed()
}