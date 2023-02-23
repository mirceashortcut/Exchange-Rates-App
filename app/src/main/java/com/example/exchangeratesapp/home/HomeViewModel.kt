package com.example.exchangeratesapp.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangeratesapp.NetworkModule
import com.example.exchangeratesapp.rates.Currency
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val ratesApi = NetworkModule.ratesApi

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val response =
                ratesApi.getExchangeRatesEur(BASE_COIN).rates

            for (rate in response) {
                _uiState.update {
                    it.copy(
                        currencies = it.currencies + listOf(Currency(rate.key, rate.value))
                    )
                }
            }
        }
    }

    data class UiState(
        val currencies: List<Currency> = emptyList()
    )

    companion object {
        const val BASE_COIN = "EUR"
    }
}