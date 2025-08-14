package ir.miare.androidcodechallenge.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.miare.androidcodechallenge.data.repository.FollowRepositoryImpl
import ir.miare.androidcodechallenge.data.repository.PlayersRepositoryImpl
import ir.miare.androidcodechallenge.domain.repository.FollowRepository
import ir.miare.androidcodechallenge.domain.repository.PlayersRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    @Binds
    @Singleton
    abstract fun bindPlayersRepository(impl: PlayersRepositoryImpl): PlayersRepository
    
    @Binds
    @Singleton
    abstract fun bindFollowRepository(impl: FollowRepositoryImpl): FollowRepository
}


