package ir.miare.androidcodechallenge.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.miare.androidcodechallenge.core.database.model.LeagueEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LeagueDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(leagues: List<LeagueEntity>)

    @Query("SELECT * FROM leagues")
    fun getAllLeagues(): Flow<List<LeagueEntity>>

    @Query("SELECT * FROM leagues ORDER BY rank ASC")
    fun getLeaguesSortedByRank(): Flow<List<LeagueEntity>>

    @Query(
        """
        SELECT l.*
        FROM leagues AS l
        JOIN teams AS t ON t.leagueId = l.leagueId
        JOIN players AS p ON p.teamId = t.teamId
        GROUP BY l.leagueId
        ORDER BY CAST(SUM(p.goalsScored) AS REAL) / NULLIF(l.totalMatches, 0) DESC
        """
    )
    fun getLeaguesSortedByGoalAverage(): Flow<List<LeagueEntity>>
}