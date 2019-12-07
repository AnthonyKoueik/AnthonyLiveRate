package com.anthony.revolut.domain

import com.anthony.revolut.data.entity.LatestRatesResponse
import com.anthony.revolut.data.entity.Rates
import com.anthony.revolut.data.remote.ApiService
import com.anthony.revolut.data.remote.RatesRemoteDataSource
import com.anthony.revolut.data.repository.RatesRepository
import com.anthony.revolut.ui.MainActivityViewModelTest
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.io.IOException
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

   // private val repository: RatesRepository = Mockito.mock(RatesRepository::class.java)

    private var apiService = Mockito.mock(ApiService::class.java)

    private var remoteDataSource: RatesRemoteDataSource = RatesRemoteDataSource(apiService)

    private var repository: RatesRepository = RatesRepository(remoteDataSource)

    private val useCase = GetRatesUseCase(repository)
    val rates = emptyList<Rates>()
    
    @Before
    fun setup(){
        `when`(useCase.getRates("EUR")).thenReturn(Single.just(currencyRateResponseForEUR))
        `when`(useCase.getRates("USD")).thenReturn(Single.just(currencyRateResponseForUSD))
        `when`(useCase.getRates("PLN")).thenReturn(Single.error(IOException("Internet Error")))
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

    @Test
    fun `given PLN Base Currency then Return Exception response`() {

        useCase.getRates("PLN").test()
            .assertError(Exception("Internet Error"))
    }

}