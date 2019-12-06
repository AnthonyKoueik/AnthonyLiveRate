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

        val map1 = mapOf(Pair("USD", 1.1652), Pair("PLN", 4.3248))
        val map2 = mapOf(Pair("EUR", 0.85646), Pair("PLN", 3.6984))

        val currencyRateResponseForEUR = LatestRatesResponse("", "EUR", "12/6/2019", map1)

        val currencyRateResponseForUSD = LatestRatesResponse("", "USD", "12/6/2019", map2)

        `when`(apiService.getRates("EUR")).thenReturn(Single.just(currencyRateResponseForEUR))
        `when`(apiService.getRates("USD")).thenReturn(Single.just(currencyRateResponseForUSD))

        viewModel = MainActivityViewModel(ratesUseCase)
    }

    @Test
    fun `Given Base Currency and Correct Api - When Getting Latest Rates - Then Return Results`(){
        viewModel.liveData.observeForever {   rates ->
            Timber.d("Rates size %s" , rates.data?.size)
            Assert.assertEquals(7, rates?.data?.size)
        }
    }
}