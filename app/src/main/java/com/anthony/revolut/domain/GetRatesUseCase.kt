package com.anthony.revolut.domain

import com.anthony.revolut.data.entity.LatestRatesResponse
import io.reactivex.Single


/**
 * Created by Anthony Koueik on 12/7/2019.
 * KOA
 * anthony.koueik@gmail.com
 */
interface GetRatesUseCase {

    fun getRates(base : String) : Single<LatestRatesResponse>
}