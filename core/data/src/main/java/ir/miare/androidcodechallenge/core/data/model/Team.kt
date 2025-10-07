package ir.miare.androidcodechallenge.core.data.model

import ir.miare.androidcodechallenge.core.model.Team
import ir.miare.androidcodechallenge.core.network.model.NetworkTeam

fun NetworkTeam.asTeam() = Team(
    name = this.name,
    rank = this.rank
)