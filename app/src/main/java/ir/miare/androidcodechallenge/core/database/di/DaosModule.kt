package ir.miare.androidcodechallenge.core.database.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.miare.androidcodechallenge.core.database.AppDatabase
import ir.miare.androidcodechallenge.core.database.dao.LeagueDao
import ir.miare.androidcodechallenge.core.database.dao.PlayerDao
import ir.miare.androidcodechallenge.core.database.dao.TeamDao

@Module
@InstallIn(SingletonComponent::class)

internal object DaosModule {
    @Provides
    fun providesLeagueDao(
        database: AppDatabase,
    ): LeagueDao = database.leagueDao()

    @Provides
    fun providesPlayerDao(
        database: AppDatabase,
    ): PlayerDao = database.playerDao()

    @Provides
    fun providesTeamDao(
        database: AppDatabase,
    ): TeamDao = database.teamDao()
}