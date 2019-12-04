package com.anthony.revolut

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.facebook.drawee.backends.pipeline.Fresco
import timber.log.Timber

class MyApplication : MultiDexApplication(){

    companion object {
        lateinit var instance: MyApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        // My App instance
        instance = this

        // Initialize Fresco
        Fresco.initialize(this)
        //FLog.setMinimumLoggingLevel(FLog.VERBOSE)

        // Initialize Debugger
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}