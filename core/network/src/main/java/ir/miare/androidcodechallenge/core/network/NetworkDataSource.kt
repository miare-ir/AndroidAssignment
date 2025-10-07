package ir.miare.androidcodechallenge.core.network

import ir.miare.androidcodechallenge.core.network.model.NetworkFakeData
import ir.miare.androidcodechallenge.core.network.util.ApiResult

interface NetworkDataSource {
    suspend fun getHome(): ApiResult<List<NetworkFakeData>>
}