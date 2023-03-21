package com.example.exchangeratesapp.di

import android.content.Context
import androidx.room.Room
import com.example.exchangeratesapp.database.RatesDao
import com.example.exchangeratesapp.database.RatesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RatesDatabase {
        return Room.databaseBuilder(context.applicationContext, RatesDatabase::class.java, "rates")
            .build()
    }

    @Provides
    @Singleton
    fun provideRatesDao(database: RatesDatabase): RatesDao {
        return database.getRatesDao()
    }
}