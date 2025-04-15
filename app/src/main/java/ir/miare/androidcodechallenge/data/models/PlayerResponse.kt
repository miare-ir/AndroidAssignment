package ir.miare.androidcodechallenge.data.models

data class PlayerResponse(
    val name: String,
    val teamResponse: TeamResponse,
    val totalGoal: Int
)
