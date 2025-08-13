package ir.miare.androidcodechallenge.data.remote

import ir.logicbase.mockfit.Mock
import ir.miare.androidcodechallenge.data.model.LeagueData
import retrofit2.http.GET

interface ApiService {
    @Mock("data.json")
    @GET("list")
    suspend fun getLeagueData(): List<LeagueData>
}