package ir.miare.androidcodechallenge

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class HiltTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, "dagger.hilt.android.testing.HiltTestApplication", context)
    }
}
