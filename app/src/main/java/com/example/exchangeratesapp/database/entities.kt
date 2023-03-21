package com.example.exchangeratesapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.exchangeratesapp.rates.ExchangeRatesResponse

@Entity
data class ExchangeRatesEntity(
    // TODO: Look into composite key
    val timestamp: Long,
    val baseCurrency: String,
    @PrimaryKey
    val to: String,
    val value: Float
)

fun ExchangeRatesResponse.asEntity() = rates.map { rate ->
    ExchangeRatesEntity(
        timestamp = timestamp,
        baseCurrency = base,
        to = rate.key,
        value = rate.value
    )
}