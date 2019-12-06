package com.anthony.revolut.data.remote

import com.anthony.revolut.data.entity.LatestRatesResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by Anthony Koueik on 12/5/2019.
 * KOA
 * anthony.koueik@gmail.com
 */
interface ApiService {

    companion object {
        const val BASE_PATH = ""
    }

    @GET("$BASE_PATH/latest")
    fun getRates(@Query("base") base: String): Single<LatestRatesResponse>
}