package ir.miare.androidcodechallenge.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import ir.miare.androidcodechallenge.data.model.LeagueData
import ir.miare.androidcodechallenge.data.remote.ApiService
import ir.miare.androidcodechallenge.domain.repository.LeagueRepository

class LeagueRepositoryImpl(private val apiService: ApiService) : LeagueRepository {

    override fun getLeaguePager(pageSize: Int): Pager<Int, LeagueData> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                LeaguePagingSource(apiService, pageSize)
            }
        )
    }
}