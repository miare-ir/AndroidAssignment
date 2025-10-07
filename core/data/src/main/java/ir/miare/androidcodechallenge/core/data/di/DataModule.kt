package ir.miare.androidcodechallenge.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.miare.androidcodechallenge.core.data.repository.DefaultLeagueRepository
import ir.miare.androidcodechallenge.core.domain.LeagueRepository

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsUserDataRepository(
        defaultLeagueRepository: DefaultLeagueRepository
    ): LeagueRepository
}