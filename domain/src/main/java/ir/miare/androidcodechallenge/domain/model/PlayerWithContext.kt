package ir.miare.androidcodechallenge.domain.model

data class PlayerWithContext(
    val id: PlayerId,
    val name: String,
    val totalGoals: Int,
    val teamName: String,
    val teamRank: Int,
    val leagueName: String,
    val leagueCountry: String,
    val leagueRank: Int,
    val leagueTotalMatches: Int
)
