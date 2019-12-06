package com.anthony.revolut.data.repository

import com.anthony.revolut.data.RatesDataSource
import com.anthony.revolut.data.entity.LatestRatesResponse
import com.anthony.revolut.data.remote.RatesRemoteDataSource
import com.anthony.revolut.di.qualifier.RemoteData
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by Anthony Koueik on 12/5/2019.
 * KOA
 * anthony.koueik@gmail.com
 */
@Singleton
class RatesRepository @Inject constructor(
    @RemoteData private val remoteDataSource: RatesDataSource
) : RatesDataSource {


    override fun getRates(base: String): Single<LatestRatesResponse> {
        return remoteDataSource.getRates(base)
    }



}