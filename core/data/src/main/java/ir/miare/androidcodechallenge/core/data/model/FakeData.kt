package ir.miare.androidcodechallenge.core.data.model

import ir.miare.androidcodechallenge.core.model.FakeData
import ir.miare.androidcodechallenge.core.network.model.NetworkFakeData

fun NetworkFakeData.asFakeData() = FakeData(
    league = this.league.asLeague(),
    players = this.players.map { it.asPlayer() }
)