package ir.miare.androidcodechallenge.domain.use_case

import androidx.paging.PagingData
import ir.miare.androidcodechallenge.data.model.LeagueData
import ir.miare.androidcodechallenge.domain.repository.LeagueRepository
import ir.miare.androidcodechallenge.presentation.league_data_list.RankingSort
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLeagueDataUseCase @Inject constructor(
    private val repository: LeagueRepository
) {
    operator fun invoke(
        pageSize: Int,
        sort: RankingSort
    ): Flow<PagingData<LeagueData>> {
        return repository.getLeaguePager(pageSize, sort).flow
    }
}
