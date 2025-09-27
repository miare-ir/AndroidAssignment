package ir.miare.androidcodechallenge.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.miare.androidcodechallenge.core.database.model.PlayerEntity
import ir.miare.androidcodechallenge.core.model.PlayerWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(players: List<PlayerEntity>)

    @Query(
        """
        SELECT 
            p.playerId, 
            p.playerName, 
            p.goalsScored, 
            p.teamId, 
            p.isFollowed,
            t.teamName,
            l.leagueName
        FROM players AS p
        INNER JOIN teams AS t ON p.teamId = t.teamId
        INNER JOIN leagues AS l ON t.leagueId = l.leagueId
        ORDER BY 
            CASE WHEN :sortKey = 'goals_scored' THEN p.goalsScored END DESC,
            CASE WHEN :sortKey = 'league_rank' THEN l.rank END ASC,
            CASE WHEN :sortKey = 'team_rank' THEN t.rank END ASC
        LIMIT :pageSize OFFSET :offset
    """
    )
    fun getPlayersPagedSorted(
        pageSize: Int,
        offset: Int,
        sortKey: String
    ): Flow<List<PlayerWithDetails>>

    @Query("UPDATE players SET isFollowed = :isFollowed WHERE playerId = :playerId")
    suspend fun updatePlayerFollowStatus(playerId: String, isFollowed: Boolean)

    @Query(
        """
        SELECT 
            p.playerId, 
            p.playerName, 
            p.goalsScored, 
            p.teamId, 
            p.isFollowed,
            t.teamName,
            l.leagueName
        FROM players AS p
        INNER JOIN teams AS t ON p.teamId = t.teamId
        INNER JOIN leagues AS l ON t.leagueId = l.leagueId
        WHERE p.isFollowed = 1
    """
    )
    fun getFollowedPlayers(): Flow<List<PlayerWithDetails>>

    @Query("SELECT COUNT(*) FROM players")
    suspend fun countPlayers(): Int
}
