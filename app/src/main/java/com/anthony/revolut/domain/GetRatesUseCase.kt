package com.anthony.revolut.domain

import android.content.Context
import com.anthony.revolut.MyApplication
import com.anthony.revolut.R
import com.anthony.revolut.data.entity.LatestRatesResponse
import com.anthony.revolut.data.repository.RatesRepository
import com.google.gson.stream.MalformedJsonException
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
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