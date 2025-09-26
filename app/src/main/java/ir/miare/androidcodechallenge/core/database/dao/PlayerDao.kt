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
    """
    )
    fun getAllPlayers(): Flow<List<PlayerWithDetails>>

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
        ORDER BY p.goalsScored DESC
        LIMIT :pageSize OFFSET :offset
    """
    )
    fun getPlayersSortedByGoals(pageSize: Int, offset: Int): Flow<List<PlayerWithDetails>>

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
        ORDER BY l.rank ASC
        LIMIT :pageSize OFFSET :offset
    """
    )
    fun getPlayersSortedByLeagueRank(pageSize: Int, offset: Int): Flow<List<PlayerWithDetails>>

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
        ORDER BY t.rank ASC
        LIMIT :pageSize OFFSET :offset
    """
    )
    fun getPlayersSortedByTeamRank(pageSize: Int, offset: Int): Flow<List<PlayerWithDetails>>

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
}
