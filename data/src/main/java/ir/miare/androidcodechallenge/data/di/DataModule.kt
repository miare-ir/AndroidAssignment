package ir.miare.androidcodechallenge.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.serialization.json.Json
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.miare.androidcodechallenge.data.repository.AssetPlayersRepository
import ir.miare.androidcodechallenge.data.repository.PreferencesFollowRepository
import ir.miare.androidcodechallenge.domain.repo.FollowRepository
import ir.miare.androidcodechallenge.domain.repo.PlayersRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create { context.preferencesDataStoreFile("follow_store") }

    @Provides
    @Singleton
    fun providePlayersRepository(
        @ApplicationContext context: Context, json: Json
    ): PlayersRepository =
        AssetPlayersRepository(context, json)

    @Provides
    @Singleton
    fun provideFollowRepository(dataStore: DataStore<Preferences>): FollowRepository =
        PreferencesFollowRepository(dataStore)
}


