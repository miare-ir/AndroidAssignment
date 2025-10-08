package ir.miare.androidcodechallenge.core.model

data class Player(
    val id: Long = 0,
    val name: String,
    val team: Team,
    val totalGoal: Int
)
