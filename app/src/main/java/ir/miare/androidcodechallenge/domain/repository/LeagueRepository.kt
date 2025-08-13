package ir.miare.androidcodechallenge.domain.repository

import androidx.paging.Pager
import ir.miare.androidcodechallenge.data.model.LeagueData

interface LeagueRepository {
    fun getLeaguePager(pageSize: Int): Pager<Int, LeagueData>
}