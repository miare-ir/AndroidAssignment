package ir.miare.androidcodechallenge.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.miare.androidcodechallenge.data.remote.ApiService
import ir.miare.androidcodechallenge.data.repository.LeagueRepositoryImpl
import ir.miare.androidcodechallenge.domain.repository.LeagueRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCardToCardRepository(
        api: ApiService
    ): LeagueRepository = LeagueRepositoryImpl(api)


}