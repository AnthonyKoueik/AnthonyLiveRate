package com.anthony.revolut.data.entity


import com.google.gson.annotations.SerializedName

data class LatestRatesResponse(
    @SerializedName("error")
    val error: String,
    @SerializedName("base")
    val base: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("rates")
    val rates: Map<String, Double>
)