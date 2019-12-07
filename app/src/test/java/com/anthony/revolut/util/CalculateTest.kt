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


    // val rates
    companion object {
        private val rateForUSD = HashMap<String, Double>()

        init {
            rateForUSD.apply {
                put("EUR", 0.86295)
                put("GBP", 0.77424)
            }
        }
    }


    @Test
    fun `Given empty map When currency provided returns no result`() {
        val currencyValue = calculate(
            emptyMap(),
            Currency.getInstance("EUR"),
            100.0
        )
        assertNull(currencyValue)
    }

    @Test
    fun `Given usd based rates When currency provided is EUR Then returns valid results`() {
        val currencyValue = calculate(
            rateForUSD,
            Currency.getInstance("EUR"),
            1.00
        )
        assertEquals(0.86295, currencyValue?.rate)
    }

    @Test
    fun `Given zero exchange When currency provided is EUR  Then returns zero as result`() {
        val currencyValue = calculate(
            rateForUSD,
            Currency.getInstance("EUR"),
            0.0
        )
        assertEquals(0.0, currencyValue?.rate)
    }
}