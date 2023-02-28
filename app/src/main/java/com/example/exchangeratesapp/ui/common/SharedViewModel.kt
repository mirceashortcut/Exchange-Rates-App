package com.example.exchangeratesapp.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangeratesapp.domain.repository.RatesRepository
import com.example.exchangeratesapp.rates.Currency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: RatesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    val uiEvents = UiEvents(
        onCurrencyChanged = ::onCurrencyChanged
    )

    init {
        loadExchangeRates(DEFAULT_BASE_COIN)
    }

    private fun onCurrencyChanged(newCurrency: String) {
        _uiState.update {
            it.copy(currencies = emptyList())
        }

        loadExchangeRates(newCurrency)
    }

    private fun loadExchangeRates(currency: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, selectedCurrency = currency)
            }

            val response = repository.getExchangeRates(currency).rates

            for (rate in response) {
                _uiState.update {
                    it.copy(
                        currencies = it.currencies + listOf(Currency(rate.key, rate.value))
                    )
                }
            }

            _uiState.update {
                it.copy(isLoading = false)
            }
        }
    }

    data class UiState(
        val currencies: List<Currency> = emptyList(),
        val selectedCurrency: String = "",
        val isLoading: Boolean = false
    )

    data class UiEvents(
        val onCurrencyChanged: (String) -> Unit = {}
    )

    companion object {
        const val DEFAULT_BASE_COIN = "EUR"
    }
}