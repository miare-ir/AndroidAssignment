package ir.miare.androidcodechallenge

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import ir.miare.androidcodechallenge.core.data.repository.PlayerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {
    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @Inject
    lateinit var repository: PlayerRepository

    override fun onCreate() {
        super.onCreate()
        appScope.launch { repository.ensureSeeded() }
    }
}