package ir.miare.androidcodechallenge.core.model

data class LeagueModel(
    val id: String,
    val name: String,
    val country: String,
    val rank: Int,
    val totalMatches: Int,
    val imageUrl: String?
)