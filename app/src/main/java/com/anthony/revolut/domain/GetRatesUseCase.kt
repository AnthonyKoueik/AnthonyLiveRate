package com.anthony.revolut.domain

import com.anthony.revolut.data.entity.LatestRatesResponse
import com.anthony.revolut.data.repository.RatesRepository
import io.reactivex.Single
import javax.inject.Inject


/**
 * Created by Anthony Koueik on 12/5/2019.
 * KOA
 * anthony.koueik@gmail.com
 */
class GetRatesUseCase @Inject constructor(private val ratesRepository: RatesRepository){


    /**
     * Returns the rates using a base symbol
     */
    fun getRates(base: String): Single<LatestRatesResponse> {
        return ratesRepository.getRates(base)
    }
}