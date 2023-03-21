package com.example.exchangeratesapp.database

import androidx.room.*

@Dao
interface RatesDao {
    // TODO: adapt to suspend fun
    @Query("select * from exchangeratesentity")
    suspend fun getRates(): List<ExchangeRatesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRates(rates: List<ExchangeRatesEntity>)
}

@Database(entities = [ExchangeRatesEntity::class], version = 1)
abstract class RatesDatabase : RoomDatabase() {
    abstract fun getRatesDao(): RatesDao
}