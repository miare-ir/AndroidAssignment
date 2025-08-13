package ir.miare.androidcodechallenge.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import ir.miare.androidcodechallenge.data.model.LeagueData
import ir.miare.androidcodechallenge.data.remote.ApiService
import ir.miare.androidcodechallenge.domain.repository.LeagueRepository
import ir.miare.androidcodechallenge.presentation.league_data_list.RankingSort

class LeagueRepositoryImpl(
    private val apiService: ApiService
) : LeagueRepository {

    override fun getLeaguePager(pageSize: Int, sort: RankingSort): Pager<Int, LeagueData> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                LeaguePagingSource(
                    apiService = apiService,
                    pageSize = pageSize,
                    sort = sort
                )
            }
        )
    }
}
