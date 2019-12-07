package com.anthony.revolut.data.repository

import com.anthony.revolut.data.entity.LatestRatesResponse
import io.reactivex.Single


/**
 * Created by Anthony Koueik on 12/7/2019.
 * KOA
 * anthony.koueik@gmail.com
 */
interface RatesRepository {

    fun getRates(base : String) : Single<LatestRatesResponse>
}