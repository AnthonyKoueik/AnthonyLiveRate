package com.anthony.revolut.domain

import com.anthony.revolut.data.entity.LatestRatesResponse
import com.anthony.revolut.data.entity.Rates
import com.anthony.revolut.data.remote.ApiService
import com.anthony.revolut.data.remote.RatesRemoteDataSource
import com.anthony.revolut.data.repository.RatesRepository
import com.anthony.revolut.data.repository.RatesRepositoryImpl
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.lang.Exception
import java.util.HashMap


/**
 * Created by Anthony Koueik on 12/7/2019.
 * KOA
 * anthony.koueik@gmail.com
 */
class GetRatesUseCaseTest {

    // val rates
    companion object {
        private val rateForUSD = HashMap<String, Double>()
        private val rateForEURO = HashMap<String, Double>()

        private val currencyRateResponseForEUR: LatestRatesResponse
        private val currencyRateResponseForUSD: LatestRatesResponse

        init {
            rateForUSD.apply {
                put("EUR", 0.86195)
                put("GBP", 0.77424)
            }
            rateForEURO.apply {
                put("USD", 1.1652)
                put("PLN", 4.3248)
            }

            currencyRateResponseForUSD = LatestRatesResponse("", "USD", "12/6/2019", rateForUSD)
            currencyRateResponseForEUR = LatestRatesResponse("", "EUR", "12/6/2019", rateForEURO)


        }
    }

    private val useCase = Mockito.mock(GetRatesUseCase::class.java)
    val rates = emptyList<Rates>()
    
    @Before
    fun setup(){
        `when`(useCase.getRates("EUR")).thenReturn(Single.just(currencyRateResponseForEUR))
        `when`(useCase.getRates("USD")).thenReturn(Single.just(currencyRateResponseForUSD))
    }

    @Test
    fun `given USD Base Currency then Return USD rates response`() {

        useCase.getRates("USD").test()
            .assertValue(currencyRateResponseForUSD)
    }

    @Test
    fun `given EUR Base Currency then Return EUR rates response`() {

        useCase.getRates("EUR").test()
            .assertValue(currencyRateResponseForEUR)
    }

  /*  @Test
    fun `given PLN Base Currency then Return Exception response`() {

        useCase.getRates("PLN").test()
            .assertError(Exception("Internet Error"))
    }*/

}