package ir.miare.androidcodechallenge.core.model.networkmodel

data class PlayerModel(
    val id: String,
    val name: String,
    val teamId: String,
    val teamName: String,
    val leagueName: String,
    val goals: Int,
    val isFollowed: Boolean,
    val imageUrl: String?
)
