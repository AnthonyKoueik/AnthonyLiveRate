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
class GetRatesUseCaseImpl @Inject constructor(private val ratesRepository: RatesRepository) :
    GetRatesUseCase {


    /**
     * Returns the rates using a base symbol
     */
    override fun getRates(base: String): Single<LatestRatesResponse> {
        return ratesRepository.getRates(base)
    }
}