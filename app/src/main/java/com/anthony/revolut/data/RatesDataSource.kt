package com.anthony.revolut.data

import com.anthony.revolut.data.entity.LatestRatesResponse
import io.reactivex.Flowable


/**
 * Created by Anthony Koueik on 12/5/2019.
 * KOA
 * anthony.koueik@gmail.com
 */
interface RatesDataSource {

    /**
     * Wrapper for my data need
     */
    fun getRates(base : String) : Flowable<LatestRatesResponse>
}