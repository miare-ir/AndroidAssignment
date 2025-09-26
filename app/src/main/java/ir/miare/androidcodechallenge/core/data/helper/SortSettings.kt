package ir.miare.androidcodechallenge.core.data.helper

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import ir.miare.androidcodechallenge.core.model.SortMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SortSettings @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {
    private val SORT_MODE_KEY: Preferences.Key<Int> = intPreferencesKey("sort_mode")

    val sortMode: Flow<SortMode> = dataStore.data.map { prefs ->
        val ordinal = prefs[SORT_MODE_KEY] ?: SortMode.DEFAULT.ordinal
        SortMode.entries.toTypedArray().getOrElse(ordinal) { SortMode.DEFAULT }
    }

    suspend fun setSortMode(mode: SortMode) {
        dataStore.edit { it[SORT_MODE_KEY] = mode.ordinal }
    }
}