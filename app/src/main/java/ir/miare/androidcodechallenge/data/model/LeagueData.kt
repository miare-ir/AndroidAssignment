package ir.miare.androidcodechallenge.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class LeagueData(
    @JsonProperty("league") var league: League,
    @JsonProperty("players") var players: List<Player>
)