package ir.miare.androidcodechallenge.domain.use_case

import ir.miare.androidcodechallenge.data.model.LeagueData
import ir.miare.androidcodechallenge.data.model.Result
import ir.miare.androidcodechallenge.domain.repository.LeagueRepository
import ir.miare.androidcodechallenge.domain.util.safeApiCallFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLeagueDataUseCase @Inject constructor(
    private val leagueRepository: LeagueRepository
) {
    operator fun invoke(): Flow<Result<List<LeagueData>>> {
        return safeApiCallFlow {
            leagueRepository.getLeagueData()
        }
    }
}
