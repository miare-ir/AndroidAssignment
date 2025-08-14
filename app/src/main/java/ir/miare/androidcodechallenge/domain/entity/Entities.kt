package ir.miare.androidcodechallenge.domain.entity

data class Player(
    val name: String,
    val team: Team,
    val league: League,
    val totalGoal: Int
)

data class Team(
    val name: String,
    val rank: Int
)

data class League(
    val name: String,
    val country: String,
    val rank: Int,
    val totalMatches: Int
)
