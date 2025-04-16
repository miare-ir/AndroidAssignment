package ir.miare.androidcodechallenge.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerResponse(
    @SerialName("name") val name: String,
    @SerialName("team") val teamResponse: TeamResponse,
    @SerialName("total_goal") val totalGoal: Int
)
