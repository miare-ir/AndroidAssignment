package ir.miare.androidcodechallenge.core.model

data class TeamModel(
    val id: String,
    val name: String,
    val rank: Int,
    val leagueId: String,
    val imageUrl: String?
)
