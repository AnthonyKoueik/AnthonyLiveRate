package com.anthony.revolut.data.remote

import com.anthony.revolut.data.RatesDataSource
import com.anthony.revolut.data.entity.LatestRatesResponse
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by Anthony Koueik on 12/5/2019.
 * KOA
 * anthony.koueik@gmail.com
 */
@Singleton
class RatesRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    RatesDataSource {

    override fun getRates(base: String): Flowable<LatestRatesResponse> {
        return apiService.getRates(base)
    }
}