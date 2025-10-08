package ir.miare.androidcodechallenge.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.miare.androidcodechallenge.core.database.entity.FollowedPlayerEntity

@Dao
interface FollowedPlayerDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: FollowedPlayerEntity): Long

    @Query("SELECT * FROM followed_players WHERE id = :playerId LIMIT 1")
    suspend fun findByPlayerId(playerId: Long): FollowedPlayerEntity?

    @Delete
    suspend fun delete(entity: FollowedPlayerEntity)
}
