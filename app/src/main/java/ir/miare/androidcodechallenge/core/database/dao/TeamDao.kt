package ir.miare.androidcodechallenge.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.miare.androidcodechallenge.core.database.model.TeamEntity

@Dao
interface TeamDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(teams: List<TeamEntity>)

    @Query("SELECT * FROM teams ORDER BY rank ASC")
    fun getTeamsSortedByRank(): List<TeamEntity>
}