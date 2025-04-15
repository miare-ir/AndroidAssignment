package ir.miare.androidcodechallenge.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import ir.miare.androidcodechallenge.data.repositories.TopScorersRepositoryImpl
import ir.miare.androidcodechallenge.domain.repositories.TopScorersRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideTopScorersRepository(
        httpClient: HttpClient
    ): TopScorersRepository {
        return TopScorersRepositoryImpl(
            client = httpClient
        )
    }

}