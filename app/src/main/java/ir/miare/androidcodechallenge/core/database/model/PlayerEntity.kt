package ir.miare.androidcodechallenge.core.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "players",
    foreignKeys = [
        ForeignKey(
            entity = TeamEntity::class,
            parentColumns = ["teamId"],
            childColumns = ["teamId"],
            onDelete = CASCADE
        )
    ],
    indices = [
        Index("teamId")
    ]
)
data class PlayerEntity(
    @PrimaryKey val playerId: String,
    val playerName: String,
    val goalsScored: Int,
    val teamId: String,
    val isFollowed: Boolean = false
)
