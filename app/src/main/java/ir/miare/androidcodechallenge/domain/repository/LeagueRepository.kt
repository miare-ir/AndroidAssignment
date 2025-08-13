package ir.miare.androidcodechallenge.domain.repository

import androidx.paging.Pager
import ir.miare.androidcodechallenge.data.model.LeagueData
import ir.miare.androidcodechallenge.presentation.league_data_list.RankingSort

interface LeagueRepository {
    fun getLeaguePager(pageSize: Int, sort: RankingSort): Pager<Int, LeagueData>
}
