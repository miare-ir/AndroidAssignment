package ir.miare.androidcodechallenge.core.domain.usecases

import ir.miare.androidcodechallenge.core.data.repository.LeagueRepository
import ir.miare.androidcodechallenge.core.model.LeagueModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLeaguesUseCase @Inject constructor(
    private val repository: LeagueRepository
) {
    operator fun invoke(): Flow<List<LeagueModel>> = repository.getLeagues()
}