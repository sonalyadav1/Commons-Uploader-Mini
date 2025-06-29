package com.commonsuploaderimini

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.google.firebase.FirebaseApp

class CommonsUploaderApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        
        // Initialize WorkManager
        val workManagerConfig = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
        WorkManager.initialize(this, workManagerConfig)
    }
}
