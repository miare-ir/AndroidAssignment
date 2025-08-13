package ir.miare.androidcodechallenge.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface FollowedPlayerDao {

    @Query("SELECT * FROM followed_players")
    fun getFollowedPlayers(): Flow<List<FollowedPlayerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(player: FollowedPlayerEntity)

    @Query("DELETE FROM followed_players WHERE playerName = :name")
    suspend fun deleteByName(name: String)

    @Query("SELECT EXISTS(SELECT 1 FROM followed_players WHERE playerName = :name)")
    suspend fun isFollowed(name: String): Boolean
}
