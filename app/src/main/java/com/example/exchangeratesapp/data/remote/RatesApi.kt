package com.example.exchangeratesapp.data.remote

import com.example.exchangeratesapp.rates.ExchangeRatesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RatesApi {
    @GET("/exchangerates_data/latest")
    suspend fun getExchangeRatesEur(@Query("base") base: String): ExchangeRatesResponse
}