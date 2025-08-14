package ir.miare.androidcodechallenge.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "follow_store")

class FollowStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object Keys {
        val FOLLOWED_SET = stringSetPreferencesKey("followed_players")
    }

    val followedIds: Flow<Set<String>> = context.dataStore.data.map { prefs ->
        prefs[Keys.FOLLOWED_SET] ?: emptySet()
    }

    suspend fun toggleFollow(playerName: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[Keys.FOLLOWED_SET] ?: emptySet()
            prefs[Keys.FOLLOWED_SET] = if (playerName in current) current - playerName else current + playerName
        }
    }
}


