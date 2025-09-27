package ir.miare.androidcodechallenge.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.miare.androidcodechallenge.core.database.model.TeamEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(teams: List<TeamEntity>)

    @Query(
        """
        SELECT * FROM teams
        ORDER BY CASE WHEN :sortKey = 'team_rank' THEN rank END ASC
        """
    )
    fun getTeamsSorted(sortKey: String): Flow<List<TeamEntity>>
}