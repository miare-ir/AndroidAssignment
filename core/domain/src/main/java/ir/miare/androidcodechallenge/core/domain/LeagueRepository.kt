package ir.miare.androidcodechallenge.core.domain

import ir.miare.androidcodechallenge.core.network.model.NetworkFakeData
import ir.miare.androidcodechallenge.core.network.util.ApiResult
import kotlinx.coroutines.flow.Flow

interface LeagueRepository {
    fun getHome() : Flow<ApiResult<NetworkFakeData>>
}