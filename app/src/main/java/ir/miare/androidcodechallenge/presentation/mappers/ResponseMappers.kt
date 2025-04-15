package ir.miare.androidcodechallenge.presentation.mappers

import ir.miare.androidcodechallenge.domain.models.League
import ir.miare.androidcodechallenge.domain.models.Player
import ir.miare.androidcodechallenge.domain.models.Team
import ir.miare.androidcodechallenge.domain.models.TopScorers
import ir.miare.androidcodechallenge.presentation.models.UiLeague
import ir.miare.androidcodechallenge.presentation.models.UiPlayer
import ir.miare.androidcodechallenge.presentation.models.UiTeam
import ir.miare.androidcodechallenge.presentation.models.UiTopScorers

fun Team.toUi() = UiTeam(
    name = name,
    rank = rank
)

fun Player.toUi() = UiPlayer(
    name = name,
    team = team.toUi(),
    totalGoal = totalGoal
)

fun League.toUi() = UiLeague(
    name = name,
    country = country,
    rank = rank,
    totalMatches = totalMatches
)

fun TopScorers.toUi() = UiTopScorers(
    players = players.map { it.toUi() },
    league = league.toUi()
)