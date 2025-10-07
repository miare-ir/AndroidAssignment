package ir.miare.androidcodechallenge.core.data.model

import ir.miare.androidcodechallenge.core.model.Player
import ir.miare.androidcodechallenge.core.network.model.NetworkPlayer

fun NetworkPlayer.asPlayer() = Player(
    name = this.name,
    team = this.team.asTeam(),
    totalGoal = this.totalGoal
)