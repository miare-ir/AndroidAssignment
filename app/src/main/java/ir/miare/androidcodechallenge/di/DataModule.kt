package ir.miare.androidcodechallenge.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.miare.androidcodechallenge.RepositoryImpl
import ir.miare.androidcodechallenge.data.Api
import ir.miare.androidcodechallenge.domain.Repository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideRepository(api: Api): Repository{
        return RepositoryImpl(api)
    }
}