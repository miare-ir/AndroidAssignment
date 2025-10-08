package ir.miare.androidcodechallenge.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.miare.androidcodechallenge.core.data.repository.DefaultLeagueRepository
import ir.miare.androidcodechallenge.core.data.repository.DefaultPlayerRepository
import ir.miare.androidcodechallenge.core.domain.LeagueRepository
import ir.miare.androidcodechallenge.core.domain.PlayerRepository

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsLeagueRepository(
        defaultLeagueRepository: DefaultLeagueRepository
    ): LeagueRepository

    @Binds
    fun bindsPlayerRepository(
        defaultPlayerRepository: DefaultPlayerRepository
    ): PlayerRepository
}