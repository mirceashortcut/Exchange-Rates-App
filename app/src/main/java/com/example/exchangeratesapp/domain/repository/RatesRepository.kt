package com.example.exchangeratesapp.domain.repository

import com.example.exchangeratesapp.database.ExchangeRatesEntity
import com.example.exchangeratesapp.rates.ExchangeRatesResponse
import kotlinx.coroutines.flow.Flow

interface RatesRepository {
    fun getExchangeRatesFlow(baseCurrency: String, refreshTime: Int): Flow<ExchangeRatesResponse>

    suspend fun getExchangeRates(): List<ExchangeRatesEntity>
    suspend fun refreshExchangeRates(baseCurrency: String)
}