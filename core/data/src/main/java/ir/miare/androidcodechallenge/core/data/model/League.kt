package ir.miare.androidcodechallenge.core.data.model

import ir.miare.androidcodechallenge.core.model.League
import ir.miare.androidcodechallenge.core.network.model.NetworkLeague

fun NetworkLeague.asLeague() = League(
    name = this.name,
    country = this.country,
    rank = this.rank,
    totalMatches = this.totalMatches
)