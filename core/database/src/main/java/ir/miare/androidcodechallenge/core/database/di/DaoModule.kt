package ir.miare.androidcodechallenge.core.database.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.miare.androidcodechallenge.core.database.AppDatabase
import ir.miare.androidcodechallenge.core.database.dao.FollowedPlayerDao

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    fun providesWorkDao(
        database: AppDatabase,
    ): FollowedPlayerDao = database.followedPlayerDao()
}