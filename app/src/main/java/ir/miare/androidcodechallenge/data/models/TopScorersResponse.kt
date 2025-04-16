package ir.miare.androidcodechallenge.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopScorersResponse(
    @SerialName("players") val playerResponses: List<PlayerResponse>,
    @SerialName("league") val leagueResponse: LeagueResponse
)