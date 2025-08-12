package ir.miare.androidcodechallenge.data.remote

import ir.miare.androidcodechallenge.data.model.LeagueData
import retrofit2.http.GET

interface ApiService {
    @GET("list")
    suspend fun getLeagueData(): List<LeagueData>
}