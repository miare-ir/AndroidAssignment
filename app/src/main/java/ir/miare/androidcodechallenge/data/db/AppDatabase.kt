package ir.miare.androidcodechallenge.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FollowedPlayerEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun followedPlayerDao(): FollowedPlayerDao
}
