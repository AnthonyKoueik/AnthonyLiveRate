package com.anthony.revolut.data.entity


import com.google.gson.annotations.SerializedName

data class Rates(
    val symbol: String,
    val rate: Double
)