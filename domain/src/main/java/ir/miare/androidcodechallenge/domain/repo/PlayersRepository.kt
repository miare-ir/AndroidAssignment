package ir.miare.androidcodechallenge.domain.repo

import ir.miare.androidcodechallenge.domain.model.PlayerWithContext

interface PlayersRepository {
    suspend fun getAllPlayers(): List<PlayerWithContext>
}
