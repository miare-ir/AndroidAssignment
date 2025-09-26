package ir.miare.androidcodechallenge.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FootballLeague(
    val name: String,
    val country: String,
    val rank: Int,
    @SerialName("total_matches")
    val totalMatches: Int,
    @SerialName("league_logo_Url")
    val imageUrl: String?,
)