package ir.miare.androidcodechallenge.core.domain

import ir.miare.androidcodechallenge.core.model.FakeData
import ir.miare.androidcodechallenge.core.network.util.ApiResult
import kotlinx.coroutines.flow.Flow

interface LeagueRepository {
    fun getHome() : Flow<ApiResult<List<FakeData>>>
}