package ir.miare.androidcodechallenge.data

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class Player(
    @JsonProperty("name") val name: String,
    @JsonProperty("team") val team: Team,
    @JsonProperty("total_goal") val totalGoal: Int
) : Serializable
