package ir.miare.androidcodechallenge.data.remote

import com.fasterxml.jackson.annotation.JsonProperty
import ir.logicbase.mockfit.Mock
import retrofit2.http.GET

data class FakeDataResponse(
    @JsonProperty("league") val league: LeagueResponse,
    @JsonProperty("players") val players: List<PlayerResponse>
)

data class LeagueResponse(
    @JsonProperty("name") val name: String,
    @JsonProperty("country") val country: String,
    @JsonProperty("rank") val rank: Int,
    @JsonProperty("total_matches") val totalMatches: Int
)

data class PlayerResponse(
    @JsonProperty("name") val name: String,
    @JsonProperty("team") val team: TeamResponse,
    @JsonProperty("total_goal") val totalGoal: Int
)

data class TeamResponse(
    @JsonProperty("name") val name: String,
    @JsonProperty("rank") val rank: Int
)

interface ApiService {
    @Mock("data.json")
    @GET("list")
    suspend fun getData(): List<FakeDataResponse>
}


