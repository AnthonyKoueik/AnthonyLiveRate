package com.anthony.revolut.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.anthony.revolut.data.Success
import com.anthony.revolut.data.entity.LatestRatesResponse
import com.anthony.revolut.data.remote.ApiService
import com.anthony.revolut.data.remote.RatesRemoteDataSource
import com.anthony.revolut.data.repository.RatesRepositoryImpl
import com.anthony.revolut.domain.GetRatesUseCase
import com.anthony.revolut.util.TestSchedulers
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.util.*
import org.mockito.junit.MockitoJUnit


/**
 * Created by Anthony Koueik on 12/6/2019.
 * KOA
 * anthony.koueik@gmail.com
 */
class MainActivityViewModelTest {

    // val rates
    companion object {
        private val rateForUSD = HashMap<String, Double>()
        private val rateForEURO = HashMap<String, Double>()

        val currencyRateResponseForEUR: LatestRatesResponse
        val currencyRateResponseForUSD: LatestRatesResponse

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

    @Rule
    @JvmField
    val rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private var apiService = Mockito.mock(ApiService::class.java)

    var remoteDataSource: RatesRemoteDataSource = RatesRemoteDataSource(apiService)

    var repositoryImpl: RatesRepositoryImpl = RatesRepositoryImpl(remoteDataSource)

    var ratesUseCase: GetRatesUseCase = GetRatesUseCase(repositoryImpl)

    var testSchedulers : TestSchedulers = TestSchedulers()
    /**
     * Sets up Dagger components for testing.
     */
    @Rule
    @JvmField
    var mockitoRule = MockitoJUnit.rule()!!

    private lateinit var viewModel: MainActivityViewModel

    @Before
    fun init() {


      //  val repositoryImpl = Mockito.mock(RatesRepositoryImpl::class.java)

        `when`(apiService.getRates("EUR")).thenReturn(Single.just(currencyRateResponseForEUR))
        //`when`(apiService.getRates("USD")).thenReturn(Single.just(currencyRateResponseForUSD))

        viewModel = MainActivityViewModel(ratesUseCase, testSchedulers)
    }

    @Test
    fun `Given Base Currency and Correct Api - When Getting Latest Rates - Then Return Results`() {
        viewModel._liveData.observeForever { result ->
            when (result) {
                is Success -> {
                    Assert.assertEquals(2, result.data.size)
                    Assert.assertEquals(
                        " ", result.data[0].currency
                    )
                }
            }
        }
    }
}