package ir.miare.androidcodechallenge.data

import ir.logicbase.mockfit.Mock
import retrofit2.http.GET

interface Api {

    @Mock("data.json")
    @GET("list")
    suspend fun getRankings(): List<FakeData>
}