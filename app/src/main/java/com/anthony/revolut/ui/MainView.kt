package com.anthony.revolut.ui

import com.anthony.revolut.data.entity.Rates
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by Anthony Koueik on 12/5/2019.
 * KOA
 * anthony.koueik@gmail.com
 */
interface MainView {

    fun setRateList(rateList : ArrayList<Rates>)


    fun setNewAmount(amount: Double)


    fun setNewCurrency(currency: String)
}