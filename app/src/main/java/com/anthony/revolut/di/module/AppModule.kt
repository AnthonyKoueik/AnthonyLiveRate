package com.anthony.revolut.di.module

import android.app.Application
import android.content.Context
import com.anthony.revolut.MyApplication
import dagger.Binds
import dagger.Module


/**
 * Created by Anthony Koueik on 12/5/2019.
 * KOA
 * anthony.koueik@gmail.com
 */
@Module
abstract class AppModule() {

    @Binds
    abstract fun bindApplicationContext(application: MyApplication): Context

    @Binds
    abstract fun bindApplication(application: MyApplication): Application
}