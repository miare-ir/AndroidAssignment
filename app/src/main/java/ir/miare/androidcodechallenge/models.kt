package ir.miare.androidcodechallenge

import java.io.Serializable as JavaSerializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FakeData(
    @SerialName("league") val league: League,
    @SerialName("players") val players: List<Player>
)

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
) : JavaSerializable

@Serializable
data class Team(
    @SerialName("name") val name: String,
    @SerialName("rank") val rank: Int
) : JavaSerializable
