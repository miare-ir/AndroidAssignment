package ir.miare.androidcodechallenge

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import ir.miare.androidcodechallenge.core.common.network.di.ApplicationScope
import ir.miare.androidcodechallenge.core.data.repository.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {

    @Inject
    @ApplicationScope
    lateinit var scope: CoroutineScope

    @Inject
    lateinit var repository: SettingsRepository

    override fun onCreate() {
        super.onCreate()
        scope.launch { repository.ensureSeeded() }
    }
}