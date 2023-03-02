package com.example.exchangeratesapp.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangeratesapp.domain.repository.RatesRepository
import com.example.exchangeratesapp.rates.Currency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: RatesRepository
) : ViewModel() {
    private val _selectedCurrency = MutableStateFlow(DEFAULT_BASE_COIN)
    val selectedCurrency = _selectedCurrency.asStateFlow()

    private val _selectedRefreshTime = MutableStateFlow(REFRESH_TIMES_IN_MINUTES.first())
    val selectedRefreshTime = _selectedRefreshTime.asStateFlow()

    val uiState = _selectedCurrency
        .combine(_selectedRefreshTime) { currency, refreshTime ->
            Pair(currency, refreshTime)
        }.flatMapLatest {
            repository
                .getExchangeRates(baseCurrency = it.first, refreshTime = it.second)
        }.map { response ->
            val responseRates =
                response.rates.map { rates -> Currency(rates.key, rates.value) }
                    .toList()

            UiState(currencies = responseRates, isLoading = false)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), UiState())

    val uiEvents = UiEvents(
        onCurrencyChanged = ::onCurrencyChanged,
        onRefreshTimeChanged = ::onRefreshTimeChanged
    )

    private fun onCurrencyChanged(newCurrency: String) {
        _selectedCurrency.value = newCurrency
    }

    private fun onRefreshTimeChanged(newRefreshTime: Int) {
        _selectedRefreshTime.value = newRefreshTime
    }

    data class UiState(
        val currencies: List<Currency> = emptyList(),
        val isLoading: Boolean = true
    )

    data class UiEvents(
        val onCurrencyChanged: (String) -> Unit = {},
        val onRefreshTimeChanged: (Int) -> Unit = {}
    )

    companion object {
        const val DEFAULT_BASE_COIN = "EUR"
        val REFRESH_TIMES_IN_MINUTES: List<Int> = listOf(1, 5, 10)
    }
}