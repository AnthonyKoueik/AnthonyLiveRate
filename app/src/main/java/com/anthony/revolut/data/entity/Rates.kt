package com.anthony.revolut.data.entity


import java.util.*

data class Rates(
    val currency: Currency,
    val rate: Double
)