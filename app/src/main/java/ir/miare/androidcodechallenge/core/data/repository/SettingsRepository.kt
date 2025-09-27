package ir.miare.androidcodechallenge.core.data.repository

interface SettingsRepository {
    suspend fun ensureSeeded()
}