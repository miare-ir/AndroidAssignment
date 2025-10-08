package ir.miare.androidcodechallenge.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.miare.androidcodechallenge.core.database.entity.FollowedPlayerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FollowedPlayerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: FollowedPlayerEntity): Long

    @Query("SELECT * FROM followed_players WHERE stableKey = :stableKey LIMIT 1")
    suspend fun findByStableKey(stableKey: String): FollowedPlayerEntity?

    @Query(value = "SELECT * FROM followed_players")
    fun getAllPlayers(): Flow<List<FollowedPlayerEntity>>

    @Query("DELETE FROM followed_players WHERE stableKey = :stableKey")
    suspend fun delete(stableKey: String)

    @Query("SELECT stableKey FROM followed_players")
    fun observeFollowedKeys(): Flow<List<String>>

    @Query("SELECT EXISTS(SELECT 1 FROM followed_players WHERE stableKey = :key)")
    suspend fun exists(key: String): Boolean
}
