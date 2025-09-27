package ir.miare.androidcodechallenge.core.model.networkmodel

import kotlinx.serialization.Serializable

@Serializable
data class Competition(
    val league: FootballLeague,
    val players: List<Player>
)