package com.example.exchangeratesapp.rates

import kotlinx.serialization.Serializable

// Network models
@Serializable
data class ExchangeRatesResponse(
    val success: Boolean,
    val timestamp: Long,
    val base: String,
    val date: String,
    val rates: Map<String, Float>
)

// VM models
@Serializable
data class Currency(
    val symbol: String,
    val value: Float
)