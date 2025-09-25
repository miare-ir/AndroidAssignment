package ir.miare.androidcodechallenge.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.miare.androidcodechallenge.core.database.model.LeagueEntity

@Dao
interface LeagueDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(leagues: List<LeagueEntity>)

    @Query("SELECT * FROM leagues ORDER BY rank ASC")
    fun getLeaguesSortedByRank(): List<LeagueEntity>

    @Query("SELECT * FROM leagues ORDER BY totalMatches DESC")
    fun getLeaguesSortedByGoalAverage(): List<LeagueEntity>
}