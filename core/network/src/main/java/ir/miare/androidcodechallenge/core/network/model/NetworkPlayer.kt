package ir.miare.androidcodechallenge.core.network.model

data class NetworkPlayer(
    val name: String,
    val team: NetworkTeam,
    val totalGoal: Int
)
