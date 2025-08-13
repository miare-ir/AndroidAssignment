package ir.miare.androidcodechallenge.domain.use_case

import androidx.paging.PagingData
import ir.miare.androidcodechallenge.data.model.LeagueData
import ir.miare.androidcodechallenge.domain.repository.LeagueRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLeagueDataUseCase @Inject constructor(
    private val leagueRepository: LeagueRepository
) {
    operator fun invoke(pageSize: Int = 5): Flow<PagingData<LeagueData>> {
        return leagueRepository.getLeaguePager(pageSize).flow
    }
}
