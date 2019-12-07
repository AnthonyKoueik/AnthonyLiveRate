package com.anthony.revolut

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.rules.TestWatcher
import org.junit.runner.Description


/**
 * Created by Anthony Koueik on 12/7/2019.
 * KOA
 * anthony.koueik@gmail.com
 */
class DaggerTestApplicationRule : TestWatcher() {

    lateinit var  testAppComponent: TestAppComponent
        private set

    override fun starting(description: Description?) {
        super.starting(description)

        val app = ApplicationProvider.getApplicationContext<Context>() as TestMyApplication
        testAppComponent = DaggerTestAppComponent.builder().application(app).build()
        testAppComponent.inject(app)

    }
}