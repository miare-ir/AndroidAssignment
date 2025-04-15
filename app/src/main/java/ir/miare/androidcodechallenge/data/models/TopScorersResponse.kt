package ir.miare.androidcodechallenge.data.models

data class TopScorersResponse(
    val playerResponses: List<PlayerResponse>,
    val leagueResponse: LeagueResponse
)