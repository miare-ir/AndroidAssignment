package ir.miare.androidcodechallenge.core.data.repository

import ir.miare.androidcodechallenge.core.model.SortMode
import kotlinx.coroutines.flow.Flow

interface SortPreferencesRepository {
    val sortMode: Flow<SortMode>
    suspend fun setSortMode(mode: SortMode)
}