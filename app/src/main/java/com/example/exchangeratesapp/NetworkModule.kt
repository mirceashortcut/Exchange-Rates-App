package com.example.exchangeratesapp

import com.example.exchangeratesapp.rates.RatesApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {
    class RepositoryInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()

            val request = original.newBuilder()
                .header("apikey", apiKey)
                .build()

            return chain.proceed(request)
        }

        companion object {
            const val apiKey = "lRIDUe5i2XX8cujZuNf8xE0naC8cSyn5"
        }
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(RepositoryInterceptor())
        .addInterceptor(HttpLoggingInterceptor())
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    val ratesApi: RatesApi = Retrofit.Builder()
        .baseUrl("https://api.apilayer.com")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(RatesApi::class.java)
}