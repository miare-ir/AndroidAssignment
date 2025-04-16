package ir.miare.androidcodechallenge.domain.repositories

import ir.miare.androidcodechallenge.domain.models.TopScorers

interface TopScorersRepository {
    suspend fun getTopScorers(): Result<List<TopScorers>>
}