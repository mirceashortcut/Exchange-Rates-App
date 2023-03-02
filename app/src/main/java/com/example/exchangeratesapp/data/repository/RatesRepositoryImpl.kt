package com.example.exchangeratesapp.data.repository

import com.example.exchangeratesapp.data.remote.RatesApi
import com.example.exchangeratesapp.domain.repository.RatesRepository
import com.example.exchangeratesapp.rates.ExchangeRatesResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RatesRepositoryImpl(private val ratesApi: RatesApi) :
    RatesRepository {
    override fun getExchangeRates(
        baseCurrency: String,
        refreshTime: Int
    ): Flow<ExchangeRatesResponse> =
        flow {
            while (true) {
                emit(ratesApi.getExchangeRates(base = baseCurrency))

                delay((refreshTime * 60 * 1000).toLong())
            }
        }
}