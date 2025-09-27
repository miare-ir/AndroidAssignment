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

    @Query(
        """
        SELECT l.*
        FROM leagues AS l
        LEFT JOIN teams AS t ON t.leagueId = l.leagueId
        LEFT JOIN players AS p ON p.teamId = t.teamId
        GROUP BY l.leagueId
        ORDER BY 
            CASE WHEN :sortKey = 'league_rank' THEN l.rank END ASC,
            CASE WHEN :sortKey = 'league_goal_avg' THEN CAST(SUM(p.goalsScored) AS REAL) / NULLIF(l.totalMatches, 0) END DESC
        """
    )
    fun getLeaguesSorted(sortKey: String): Flow<List<LeagueEntity>>
}