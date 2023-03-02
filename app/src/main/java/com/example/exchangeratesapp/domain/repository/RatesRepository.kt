package com.example.exchangeratesapp.domain.repository

import com.example.exchangeratesapp.rates.ExchangeRatesResponse
import kotlinx.coroutines.flow.Flow

interface RatesRepository {
    fun getExchangeRates(baseCurrency: String, refreshTime: Int): Flow<ExchangeRatesResponse>
}