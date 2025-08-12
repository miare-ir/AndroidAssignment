package ir.miare.androidcodechallenge.data

import com.fasterxml.jackson.annotation.JsonProperty

data class League(
    @JsonProperty("name") val name: String,
    @JsonProperty("country") val country: String,
    @JsonProperty("rank") val rank: Int,
    @JsonProperty("total_matches") val totalMatches: Int,
)
