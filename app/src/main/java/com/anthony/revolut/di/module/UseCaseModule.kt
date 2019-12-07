package com.anthony.revolut.di.module

import com.anthony.revolut.data.repository.RatesRepositoryImpl
import com.anthony.revolut.domain.GetRatesUseCase
import com.anthony.revolut.domain.GetRatesUseCaseImpl
import com.anthony.revolut.utils.DefaultSchedulers
import com.anthony.revolut.utils.ExecutionSchedulers
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by Anthony Koueik on 12/5/2019.
 * KOA
 * anthony.koueik@gmail.com
 */
@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun providesGetRatesUseCase(ratesRepositoryImpl: RatesRepositoryImpl): GetRatesUseCase
            = GetRatesUseCaseImpl(ratesRepositoryImpl)

    @Singleton
    @Provides
    fun providesExecutionSchedulers(): ExecutionSchedulers {
        return DefaultSchedulers()
    }
}