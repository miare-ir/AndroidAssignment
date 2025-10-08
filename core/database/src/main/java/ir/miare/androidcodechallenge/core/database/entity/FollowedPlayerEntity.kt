package ir.miare.androidcodechallenge.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

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