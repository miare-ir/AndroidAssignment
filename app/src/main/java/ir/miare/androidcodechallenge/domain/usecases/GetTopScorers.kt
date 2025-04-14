package ir.miare.androidcodechallenge.domain.usecases

import ir.miare.androidcodechallenge.domain.models.TopScorers
import ir.miare.androidcodechallenge.domain.repositories.TopScorersRepository
import javax.inject.Inject

class GetTopScorers @Inject constructor(
    private val repository: TopScorersRepository
) {
    suspend operator fun invoke(): Result<List<TopScorers>> {
        return repository.getTopScorers()
    }
}