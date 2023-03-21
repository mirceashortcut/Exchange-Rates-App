package com.example.exchangeratesapp.di

import com.example.exchangeratesapp.data.remote.RatesApi
import com.example.exchangeratesapp.data.repository.RatesRepositoryImpl
import com.example.exchangeratesapp.database.RatesDao
import com.example.exchangeratesapp.domain.repository.RatesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideRatesRepository(ratesApi: RatesApi, ratesDao: RatesDao): RatesRepository {
        return RatesRepositoryImpl(ratesApi = ratesApi, ratesDao = ratesDao)
    }
}