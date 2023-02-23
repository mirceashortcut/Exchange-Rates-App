package com.example.exchangeratesapp.rates

// Network models
data class ExchangeRatesResponse(
    val success: Boolean,
    val timestamp: Long,
    val base: String,
    val date: String,
    val rates: Map<String, Float>
)

// VM models
data class Currency(
    val symbol: String,
    val value: Float
)