package com.anthony.revolut.di.module

import com.anthony.revolut.data.RatesDataSource
import com.anthony.revolut.data.remote.RatesRemoteDataSource
import com.anthony.revolut.di.qualifier.RemoteData
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


/**
 * Created by Anthony Koueik on 12/5/2019.
 * KOA
 * anthony.koueik@gmail.com
 */
@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    @RemoteData
    abstract fun bindRatesRemoteDataSource(ratesRemoteDataSource: RatesRemoteDataSource): RatesDataSource

}