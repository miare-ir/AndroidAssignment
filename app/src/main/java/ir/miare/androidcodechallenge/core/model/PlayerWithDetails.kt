package ir.miare.androidcodechallenge.core.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerWithDetails(
    val playerId: String,
    val playerName: String,
    val goalsScored: Int,
    val teamId: String,
    val teamName: String,
    val leagueName: String,
    val isFollowed: Boolean,
    val imageUrl: String? = null,
)