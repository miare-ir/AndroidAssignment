package ir.miare.androidcodechallenge.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LeagueResponse(
    @SerialName("name") val name: String,
    @SerialName("country") val country: String,
    @SerialName("rank") val rank: Int,
    @SerialName("total_matches") val totalMatches: Int,
)
