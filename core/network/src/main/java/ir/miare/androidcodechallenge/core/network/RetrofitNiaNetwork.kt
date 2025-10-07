package ir.miare.androidcodechallenge.core.network

import ir.miare.androidcodechallenge.core.network.api.home.HomeApi
import ir.miare.androidcodechallenge.core.network.model.NetworkFakeData
import ir.miare.androidcodechallenge.core.network.util.APiHelper.safeApiCall
import ir.miare.androidcodechallenge.core.network.util.ApiResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class RetrofitNetwork @Inject constructor(
    val homeApi: HomeApi,
) : NetworkDataSource {

    override suspend fun getHome(): ApiResult<NetworkFakeData> {
        return safeApiCall { homeApi.getHome() }
    }
}
