package com.anthony.revolut.di.module

import com.anthony.revolut.data.repository.RatesRepository
import com.anthony.revolut.domain.GetRatesUseCase
import dagger.Module
import dagger.Provides
import java.math.MathContext
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
    fun providesGetRatesUseCase(
        ratesRepository: RatesRepository
    ): GetRatesUseCase = GetRatesUseCase(ratesRepository)
}