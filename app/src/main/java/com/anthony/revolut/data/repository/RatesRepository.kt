package com.anthony.revolut.data.repository

import com.anthony.revolut.data.RatesDataSource
import com.anthony.revolut.data.entity.LatestRatesResponse
import com.anthony.revolut.data.remote.RatesRemoteDataSource
import com.anthony.revolut.di.qualifier.RemoteData
import io.reactivex.Flowable
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


    override fun getRates(base: String): Flowable<LatestRatesResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}