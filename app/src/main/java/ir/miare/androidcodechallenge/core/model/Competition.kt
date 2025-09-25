package ir.miare.androidcodechallenge.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Competition(
    val league: FootballLeague,
    val players: List<Player>
)
