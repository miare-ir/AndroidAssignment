package ir.miare.androidcodechallenge.data.mappers

import ir.miare.androidcodechallenge.data.models.LeagueResponse
import ir.miare.androidcodechallenge.data.models.PlayerResponse
import ir.miare.androidcodechallenge.data.models.TeamResponse
import ir.miare.androidcodechallenge.data.models.TopScorersResponse
import ir.miare.androidcodechallenge.domain.models.League
import ir.miare.androidcodechallenge.domain.models.Player
import ir.miare.androidcodechallenge.domain.models.Team
import ir.miare.androidcodechallenge.domain.models.TopScorers

fun TeamResponse.toDomain() = Team(
    name = name,
    rank = rank
)

fun PlayerResponse.toDomain() = Player(
    name = name,
    team = teamResponse.toDomain(),
    totalGoal = totalGoal
)

fun LeagueResponse.toDomain() = League(
    name = name,
    country = country,
    rank = rank,
    totalMatches = totalMatches
)

fun TopScorersResponse.toDomain() =
    TopScorers(
        players = playerResponses.map { it.toDomain() },
        league = leagueResponse.toDomain()
    )