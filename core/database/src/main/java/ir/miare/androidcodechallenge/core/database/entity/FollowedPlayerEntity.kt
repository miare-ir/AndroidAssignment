package ir.miare.androidcodechallenge.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.miare.androidcodechallenge.core.model.Player
import ir.miare.androidcodechallenge.core.model.Team

@Entity(
    tableName = "followed_players"
)
data class FollowedPlayerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val playerName: String,
    val totalGoal: Int,
    val teamName: String,
    val teamRank: Int
)

fun FollowedPlayerEntity.asPlayer() = Player(
    id = this.id,
    name = this.playerName,
    team = Team(
        name = this.teamName,
        rank = this.teamRank
    ),
    totalGoal = this.totalGoal
)