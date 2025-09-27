package ir.miare.androidcodechallenge.core.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.miare.androidcodechallenge.core.data.repository.PlayerRepository
import ir.miare.androidcodechallenge.core.domain.usecases.GetFollowedPlayersUseCase
import ir.miare.androidcodechallenge.core.domain.usecases.GetPlayersPagedUseCase
import ir.miare.androidcodechallenge.core.domain.usecases.GetSortModeUseCase
import ir.miare.androidcodechallenge.core.domain.usecases.SetPlayerFollowedUseCase
import ir.miare.androidcodechallenge.core.domain.usecases.SetSortModeUseCase


@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    fun provideGetPlayersPagedUseCase(repo: PlayerRepository) =
        GetPlayersPagedUseCase(repo)

    @Provides
    fun provideGetFollowedPlayersUseCase(repo: PlayerRepository) =
        GetFollowedPlayersUseCase(repo)

    @Provides
    fun provideGetSortModeUseCase(repo: PlayerRepository) = GetSortModeUseCase(repo)
    @Provides
    fun provideSetPlayerFollowedUseCase(repo: PlayerRepository) =
        SetPlayerFollowedUseCase(repo)

    @Provides
    fun provideSetSortModeUseCase(repo: PlayerRepository) = SetSortModeUseCase(repo)
}


