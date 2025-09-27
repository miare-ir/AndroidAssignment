package ir.miare.androidcodechallenge.core.model

import ir.miare.androidcodechallenge.core.model.networkmodel.PlayerModel
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

fun PlayerWithDetails.asExternalModel() = PlayerModel(
    id = playerId,
    name = playerName,
    teamId = teamId,
    teamName = teamName,
    leagueName = leagueName,
    goals = goalsScored,
    isFollowed = isFollowed,
    imageUrl = imageUrl,
)