package ir.miare.androidcodechallenge

import android.app.Application
import ir.miare.androidcodechallenge.core.common.network.di.ApplicationScope
import dagger.hilt.android.HiltAndroidApp
import ir.miare.androidcodechallenge.core.data.repository.PlayerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {

    @Inject
    @ApplicationScope
    lateinit var scope: CoroutineScope

    @Inject
    lateinit var repository: PlayerRepository

    override fun onCreate() {
        super.onCreate()
        scope.launch { repository.ensureSeeded() }
    }
}