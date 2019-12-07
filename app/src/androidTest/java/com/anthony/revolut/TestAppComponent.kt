package com.anthony.revolut

import com.anthony.revolut.data.repository.RatesRepository
import com.anthony.revolut.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


/**
 * Created by Anthony Koueik on 12/7/2019.
 * KOA
 * anthony.koueik@gmail.com
 */
@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class, AppModule::class, ViewModelModule::class,
    NetworkModule::class, RepositoryModule::class, ActivityBindingModule::class,
    UseCaseModule::class])
interface TestAppComponent : AndroidInjector<TestMyApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: TestMyApplication): Builder

        fun build(): TestAppComponent
    }

    val ratesRepository: RatesRepository
}