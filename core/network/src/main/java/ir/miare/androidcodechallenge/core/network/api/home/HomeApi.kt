package ir.miare.androidcodechallenge.core.network.api.home

import ir.miare.androidcodechallenge.core.network.model.NetworkFakeData
import retrofit2.Response
import retrofit2.http.GET

internal interface HomeApi {
    @GET("list")
    suspend fun getHome(): Response<NetworkFakeData>
}