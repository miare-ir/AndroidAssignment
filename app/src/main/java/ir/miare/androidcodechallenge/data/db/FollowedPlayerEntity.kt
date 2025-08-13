package ir.miare.androidcodechallenge.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "followed_players")
data class FollowedPlayerEntity(
    @PrimaryKey val playerName: String,
    val totalGoal: Int,
    val teamName: String,
    val teamRank: Int,
    val leagueName: String,
    val leagueCountry: String,
    val leagueRank: Int,
    val leagueTotalMatches: Int
)
