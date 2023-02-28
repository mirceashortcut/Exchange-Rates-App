package com.example.exchangeratesapp.domain.repository

import com.example.exchangeratesapp.rates.ExchangeRatesResponse

interface RatesRepository {
    suspend fun getExchangeRates(base: String): ExchangeRatesResponse
}