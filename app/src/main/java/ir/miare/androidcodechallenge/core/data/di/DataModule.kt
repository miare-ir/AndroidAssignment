package ir.miare.androidcodechallenge.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.miare.androidcodechallenge.core.data.repository.PlayerRepository
import ir.miare.androidcodechallenge.core.data.repository.PlayerRepositoryImpl
import ir.miare.androidcodechallenge.core.data.repository.LeagueRepository
import ir.miare.androidcodechallenge.core.data.repository.LeagueRepositoryImpl
import ir.miare.androidcodechallenge.core.data.repository.TeamRepository
import ir.miare.androidcodechallenge.core.data.repository.TeamRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindFootballRepository(
        repository: PlayerRepositoryImpl
    ): PlayerRepository

    @Binds
    fun bindLeagueRepository(
        repository: LeagueRepositoryImpl
    ): LeagueRepository

    @Binds
    fun bindTeamRepository(
        repository: TeamRepositoryImpl
    ): TeamRepository
}