package com.anthony.revolut.utils

import com.anthony.revolut.data.entity.Rates
import java.util.*


/**
 * Created by Anthony Koueik on 12/7/2019.
 * KOA
 * anthony.koueik@gmail.com
 */

fun calculate(
    currencyRatesData: Map<String, Double>,
    wantedCurrency: Currency,
    exchangedValue: Double
) : Rates? {
    val exchangeRate = currencyRatesData[wantedCurrency.currencyCode]

    return if (exchangeRate != null) {
        val calculatedValue = exchangeRate * exchangedValue
        Rates(wantedCurrency, calculatedValue)
    } else {
        null
    }
}