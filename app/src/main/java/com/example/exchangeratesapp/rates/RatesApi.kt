package com.example.exchangeratesapp.rates

import retrofit2.http.GET
import retrofit2.http.Query

interface RatesApi {
    @GET("/exchangerates_data/latest")
    suspend fun getExchangeRatesEur(@Query("base") base: String): ExchangeRatesResponse
}