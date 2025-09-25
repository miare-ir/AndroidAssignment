package ir.miare.androidcodechallenge.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.miare.androidcodechallenge.core.data.repository.FootballRepository
import ir.miare.androidcodechallenge.core.data.repository.FootballRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindFootballRepository(
        repository: FootballRepositoryImpl
    ): FootballRepository
}