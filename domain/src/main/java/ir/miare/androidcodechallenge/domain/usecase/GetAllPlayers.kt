package ir.miare.androidcodechallenge.domain.usecase

import ir.miare.androidcodechallenge.domain.model.PlayerWithContext
import ir.miare.androidcodechallenge.domain.repo.PlayersRepository
import javax.inject.Inject

class GetAllPlayers @Inject constructor(private val repository: PlayersRepository) {
    suspend operator fun invoke(): List<PlayerWithContext> = repository.getAllPlayers()
}
