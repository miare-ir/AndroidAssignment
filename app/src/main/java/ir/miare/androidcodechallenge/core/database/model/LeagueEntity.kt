package ir.miare.androidcodechallenge.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "leagues")
data class LeagueEntity(
    @PrimaryKey val leagueId: String,
    val leagueName: String,
    val country: String,
    val rank: Int,
    val totalMatches: Int,
    val imageUrl: String? = null
)
