package ir.miare.androidcodechallenge.core.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import ir.miare.androidcodechallenge.core.model.TeamModel

@Entity(
    tableName = "teams",
    foreignKeys = [
        ForeignKey(
            entity = LeagueEntity::class,
            parentColumns = ["leagueId"],
            childColumns = ["leagueId"],
            onDelete = CASCADE
        )
    ],
    indices = [
        Index("leagueId")
    ]
)
data class TeamEntity(
    @PrimaryKey val teamId: String,
    val teamName: String,
    val leagueId: String,
    val rank: Int,
    val imageUrl: String? = null,
)

fun TeamEntity.asExternalModel() = TeamModel(
    id = teamId,
    name = teamName,
    rank = rank,
    leagueId = leagueId,
    imageUrl = imageUrl,
)