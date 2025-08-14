package ir.miare.androidcodechallenge.data.remote

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class FakeData(
    @SerialName("league") var league: League,
    @SerialName("players") var players: List<Player>
){
    @Serializable
    data class League(
        @SerialName("name") val name: String,
        @SerialName("country") val country: String,
        @SerialName("rank") val rank: Int,
        @SerialName("total_matches") val totalMatches: Int,
    )

    @Serializable
    data class Player(
        @SerialName("name") val name: String,
        @SerialName("team") val team: Team,
        @SerialName("total_goal") val totalGoal: Int
    ){
        @Serializable
        data class Team(
            @SerialName("name") val name: String,
            @SerialName("rank") val rank: Int
        )
    }
}
