package ir.miare.androidcodechallenge.core.data.model

import ir.miare.androidcodechallenge.core.database.entity.FollowedPlayerEntity
import ir.miare.androidcodechallenge.core.model.Player
import ir.miare.androidcodechallenge.core.model.stableKey
import ir.miare.androidcodechallenge.core.network.model.NetworkPlayer

fun NetworkPlayer.asPlayer() = Player(
    name = this.name,
    team = this.team.asTeam(),
    totalGoal = this.totalGoal
)

fun Player.asEntity() = FollowedPlayerEntity(
    stableKey = stableKey(),
    playerName = this.name,
    totalGoal = this.totalGoal,
    teamName = this.team.name,
    teamRank = this.team.rank
)