package com.example.bugit.application

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import androidx.work.Configuration
import com.example.core.feature.bug_reporting.domain.repo.IBackgroundSyncManager
import javax.inject.Inject

@HiltAndroidApp
class BugItApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var syncManager: IBackgroundSyncManager


    override fun onCreate() {
        super.onCreate()
         syncManager.schedulePeriodicRetrySweep()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(Log.DEBUG)
            .build()
}