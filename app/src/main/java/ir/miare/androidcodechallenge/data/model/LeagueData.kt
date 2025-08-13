package ir.miare.androidcodechallenge.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class LeagueData(
    @JsonProperty("league") var league: League,
    @JsonProperty("players") var players: List<Player>
)