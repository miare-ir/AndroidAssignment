package ir.miare.androidcodechallenge.core.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import ir.miare.androidcodechallenge.core.model.Player
import ir.miare.androidcodechallenge.core.model.Team

@Entity(
    tableName = "followed_players",
    indices = [Index(value = ["stableKey"], unique = true)]
)
data class FollowedPlayerEntity(
    @PrimaryKey val stableKey: String,
    val playerName: String,
    val totalGoal: Int,
    val teamName: String,
    val teamRank: Int
)

fun FollowedPlayerEntity.asPlayer() = Player(
    name = this.playerName,
    team = Team(
        name = this.teamName,
        rank = this.teamRank
    ),
    totalGoal = this.totalGoal,
    isFollowed = true
)