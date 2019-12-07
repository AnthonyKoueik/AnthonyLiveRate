package com.anthony.revolut.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.anthony.revolut.data.entity.LatestRatesResponse
import com.anthony.revolut.data.remote.ApiService
import com.anthony.revolut.data.remote.RatesRemoteDataSource
import com.anthony.revolut.data.repository.RatesRepository
import com.anthony.revolut.domain.GetRatesUseCase
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.util.*
import javax.inject.Inject
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import timber.log.Timber


/**
 * Created by Anthony Koueik on 12/6/2019.
 * KOA
 * anthony.koueik@gmail.com
 */
class MainActivityViewModelTest {

    // This rule is forced by LiveData trying to call MainThread

    // mocks rates
    companion object {
        private val rateForUSD = HashMap<String, Double>()
        private val rateForEURO = HashMap<String, Double>()

        val currencyRateResponseForEUR : LatestRatesResponse
        val currencyRateResponseForUSD : LatestRatesResponse

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

    var repository: RatesRepository = RatesRepository(remoteDataSource)

    var ratesUseCase: GetRatesUseCase = GetRatesUseCase(repository)

    /**
     * Sets up Dagger components for testing.
     */
    @Rule
    @JvmField
    var mockitoRule = MockitoJUnit.rule()!!

    private lateinit var viewModel: MainActivityViewModel

    @Before
    fun init() {



        `when`(apiService.getRates("EUR")).thenReturn(Single.just(currencyRateResponseForEUR))
        `when`(apiService.getRates("USD")).thenReturn(Single.just(currencyRateResponseForUSD))

        viewModel = MainActivityViewModel(ratesUseCase)
    }

    @Test
    fun `Given Base Currency and Correct Api - When Getting Latest Rates - Then Return Results`(){
        viewModel.liveData.observeForever {   rates ->
            Assert.assertEquals(7, rates?.data?.size)
        }
    }
}