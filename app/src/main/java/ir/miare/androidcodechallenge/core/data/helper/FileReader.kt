package ir.miare.androidcodechallenge.core.data.helper

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FileReader @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    fun readeJsonFile(): String {
        return context.assets.open("data.json")
            .bufferedReader()
            .use { it.readText() }
    }
}