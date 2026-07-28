package com.budgeto

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class BudgetoGlobalApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        // The default androidx.startup WorkManagerInitializer is removed in the manifest
        // (it always initializes with Configuration.Builder().build(), ignoring Configuration.Provider),
        // so WorkManager must be initialized here manually, after Hilt has injected workerFactory.
        WorkManager.initialize(this, workManagerConfiguration)
    }
}