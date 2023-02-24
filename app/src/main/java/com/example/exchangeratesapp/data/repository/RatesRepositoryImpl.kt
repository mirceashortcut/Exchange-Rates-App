package com.example.exchangeratesapp.data.repository

import com.example.exchangeratesapp.data.remote.RatesApi
import com.example.exchangeratesapp.domain.repository.RatesRepository
import com.example.exchangeratesapp.rates.ExchangeRatesResponse

class RatesRepositoryImpl(private val ratesApi: RatesApi) : RatesRepository {
    override suspend fun getExchangeRatesEur(base: String): ExchangeRatesResponse {
        return ratesApi.getExchangeRatesEur(base)
    }
}