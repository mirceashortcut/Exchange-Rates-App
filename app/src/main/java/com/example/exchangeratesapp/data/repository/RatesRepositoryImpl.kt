package com.example.exchangeratesapp.data.repository

import com.example.exchangeratesapp.data.remote.RatesApi
import com.example.exchangeratesapp.database.ExchangeRatesEntity
import com.example.exchangeratesapp.database.RatesDao
import com.example.exchangeratesapp.database.asEntity
import com.example.exchangeratesapp.domain.repository.RatesRepository
import com.example.exchangeratesapp.rates.ExchangeRatesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class RatesRepositoryImpl(private val ratesApi: RatesApi, private val ratesDao: RatesDao) :
    RatesRepository {
    override fun getExchangeRatesFlow(
        baseCurrency: String,
        refreshTime: Int
    ): Flow<ExchangeRatesResponse> =
        flow {
            while (true) {
                emit(ratesApi.getExchangeRates(base = baseCurrency))

                delay((refreshTime * 60 * 1000).toLong())
            }
        }

    override suspend fun getExchangeRates(): List<ExchangeRatesEntity> =
        withContext(Dispatchers.IO) {
            ratesDao.getRates()
        }

    override suspend fun refreshExchangeRates(baseCurrency: String) =
        withContext(Dispatchers.IO) {
            if(ratesDao.getRates().isEmpty()) {
                val response = ratesApi.getExchangeRates(baseCurrency)

                ratesDao.insertRates(response.asEntity())
            }
        }
}