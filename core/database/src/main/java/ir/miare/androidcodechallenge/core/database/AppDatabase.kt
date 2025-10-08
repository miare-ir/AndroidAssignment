package ir.miare.androidcodechallenge.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.miare.androidcodechallenge.core.database.dao.FollowedPlayerDao
import ir.miare.androidcodechallenge.core.database.entity.FollowedPlayerEntity

@Database(
    entities = [FollowedPlayerEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun followedPlayerDao(): FollowedPlayerDao
}