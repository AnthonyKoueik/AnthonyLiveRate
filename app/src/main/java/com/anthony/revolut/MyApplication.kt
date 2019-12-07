package com.anthony.revolut

import android.app.Activity
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.anthony.revolut.di.DaggerAppComponent
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

open class MyApplication : MultiDexApplication(), HasActivityInjector {

    companion object {
        lateinit var instance: MyApplication
            private set
    }

    @Inject
    lateinit var activityDispatchingInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        //DI Dagger Builder
        DaggerAppComponent.builder().application(this).build().inject(this)

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

    override fun activityInjector(): DispatchingAndroidInjector<Activity> =
        activityDispatchingInjector
}