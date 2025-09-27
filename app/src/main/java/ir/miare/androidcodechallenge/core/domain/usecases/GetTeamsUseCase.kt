package ir.miare.androidcodechallenge.core.domain.usecases

import ir.miare.androidcodechallenge.core.data.repository.TeamRepository
import ir.miare.androidcodechallenge.core.database.model.TeamEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTeamsUseCase @Inject constructor(
    private val repository: TeamRepository
) {
    operator fun invoke(): Flow<List<TeamEntity>> = repository.getTeams()
}