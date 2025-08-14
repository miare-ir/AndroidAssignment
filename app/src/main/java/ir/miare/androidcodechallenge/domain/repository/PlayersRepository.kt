package ir.miare.androidcodechallenge.domain.repository

import ir.miare.androidcodechallenge.domain.entity.Player
import ir.miare.androidcodechallenge.domain.entity.League

interface PlayersRepository {
    suspend fun fetchAllLeagues(): List<League>
    suspend fun fetchAllPlayers(): List<Player>
}
