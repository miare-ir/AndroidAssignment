package ir.miare.androidcodechallenge.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Team(
    val name: String,
    val rank: Int,
    @SerialName("logo_url")
    val imageUrl: String?,
)