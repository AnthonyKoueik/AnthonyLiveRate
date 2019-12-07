package com.anthony.revolut.util

import com.anthony.revolut.utils.calculate
import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.util.*


/**
 * Created by Anthony Koueik on 12/7/2019.
 * KOA
 * anthony.koueik@gmail.com
 */
class CalculateTest {


    // mocks rates
    companion object {
        private val rateForUSD = HashMap<String, Double>()

        init {
            rateForUSD.apply {
                put("EUR", 0.86195)
                put("GBP", 0.77424)
            }
        }
    }


    @Test
    fun `GivenEmptyMap-WhenCurrencyProvided-Returns-NoResult`() {
        val currencyValue = calculate(
            emptyMap(),
            Currency.getInstance("EUR"),
            100.0
        )
        assertNull(currencyValue)
    }

    @Test
    fun `GivenUsdBasedRates-WhenCurrencyProvidedIsEUR-Returns-ValidResults`() {
        val currencyValue = calculate(
            rateForUSD,
            Currency.getInstance("EUR"),
            1.00
        )
        assertEquals(0.86195, currencyValue?.rate)
    }

    @Test
    fun `GivenZeroExchange-WhenCurrencyProvidedIsEUR-Returns-ZeroAsResult`() {
        val currencyValue = calculate(
            rateForUSD,
            Currency.getInstance("EUR"),
            0.0
        )
        assertEquals(0.0, currencyValue?.rate)
    }
}