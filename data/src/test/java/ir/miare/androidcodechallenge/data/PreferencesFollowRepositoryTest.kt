package ir.miare.androidcodechallenge.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import ir.miare.androidcodechallenge.domain.model.PlayerId
import ir.miare.androidcodechallenge.data.repository.PreferencesFollowRepository
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

@RunWith(RobolectricTestRunner::class)
class PreferencesFollowRepositoryTest {

    private fun dataStore(context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create { context.preferencesDataStoreFile("test_follow_store") }

    @Test
    fun toggle_persists_and_emits() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val repo = PreferencesFollowRepository(dataStore(context))
        val id = PlayerId("abc")
        repo.toggleFollow(id)
        val set = repo.observeFollowed().first()
        assertEquals(true, set.contains(id))
    }
}


