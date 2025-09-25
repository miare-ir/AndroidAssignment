package ir.miare.androidcodechallenge.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.miare.androidcodechallenge.core.database.dao.LeagueDao
import ir.miare.androidcodechallenge.core.database.dao.PlayerDao
import ir.miare.androidcodechallenge.core.database.dao.TeamDao
import ir.miare.androidcodechallenge.core.database.model.LeagueEntity
import ir.miare.androidcodechallenge.core.database.model.PlayerEntity
import ir.miare.androidcodechallenge.core.database.model.TeamEntity

@Database(
    entities = [
        LeagueEntity::class,
        TeamEntity::class,
        PlayerEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun leagueDao(): LeagueDao
    abstract fun teamDao(): TeamDao
    abstract fun playerDao(): PlayerDao
}