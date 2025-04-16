package ir.miare.androidcodechallenge.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeamResponse(
    @SerialName("name") val name: String,
    @SerialName("rank") val rank: Int
)
