package ir.miare.androidcodechallenge.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import ir.miare.androidcodechallenge.domain.model.PlayerId
import ir.miare.androidcodechallenge.domain.repo.FollowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesFollowRepository(
    private val dataStore: DataStore<Preferences>
) : FollowRepository {

    private val key = stringSetPreferencesKey("followed_players")

    override fun observeFollowed(): Flow<Set<PlayerId>> = dataStore.data.map { prefs ->
        prefs[key].orEmpty().map { PlayerId(it) }.toSet()
    }

    override suspend fun toggleFollow(playerId: PlayerId) {
        dataStore.edit { prefs ->
            val current = prefs[key].orEmpty().toMutableSet()
            if (current.contains(playerId.value)) current.remove(playerId.value) else current.add(playerId.value)
            prefs[key] = current
        }
    }
}
