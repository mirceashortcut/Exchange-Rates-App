package com.example.exchangeratesapp.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangeratesapp.domain.repository.RatesRepository
import com.example.exchangeratesapp.rates.Currency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: RatesRepository
) : ViewModel() {
//    private val _selectedCurrency = MutableStateFlow(DEFAULT_BASE_COIN)
//    val selectedCurrency = _selectedCurrency.asStateFlow()
//
//    private val _selectedRefreshTime = MutableStateFlow(REFRESH_TIMES_IN_MINUTES.first())
//    val selectedRefreshTime = _selectedRefreshTime.asStateFlow()
//
//    val uiState = _selectedCurrency
//        .combine(_selectedRefreshTime) { currency, refreshTime ->
//            Pair(currency, refreshTime)
//        }.flatMapLatest {
//            repository
//                .getExchangeRatesFlow(baseCurrency = it.first, refreshTime = it.second)
//        }.map { response ->
//            val responseRates =
//                response.rates.map { rates -> Currency(rates.key, rates.value) }
//                    .toList()
//
//            UiState(currencies = responseRates, isLoading = false)
//        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), UiState())
//
//    val uiEvents = UiEvents(
//        onCurrencyChanged = ::onCurrencyChanged,
//        onRefreshTimeChanged = ::onRefreshTimeChanged
//    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    val uiEvents = UiEvents(
        onCurrencyChanged = ::onCurrencyChanged,
        onRefreshClicked = ::onRefreshClicked
    )

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, selectedCurrency = DEFAULT_BASE_COIN)
            }

            repository.refreshExchangeRates(DEFAULT_BASE_COIN)
            val entities = repository.getExchangeRates()
            val currencies = entities.map { Currency(it.to, it.value) }

            _uiState.update {
                it.copy(currencies = currencies, selectedCurrency = entities.first().baseCurrency)
            }

            _uiState.update {
                it.copy(isLoading = false)
            }
        }
    }

    private fun onCurrencyChanged(newCurrency: String) {
        _uiState.update {
            it.copy(currencies = emptyList())
        }

        loadExchangeRates(newCurrency)
    }

    private fun onRefreshClicked() {
        viewModelScope.launch {
            val entities = repository.getExchangeRates()
            val currencies = entities.map { Currency(it.to, it.value) }

            _uiState.update {
                it.copy(currencies = currencies, selectedCurrency = entities.first().baseCurrency)
            }
        }
    }

    private fun loadExchangeRates(currency: String) {

    }

    data class UiState(
        val currencies: List<Currency> = emptyList(),
        val selectedCurrency: String = "",
        val isLoading: Boolean = false
    )

    data class UiEvents(
        val onCurrencyChanged: (String) -> Unit = {},
        val onRefreshClicked: () -> Unit = {}
    )

    companion object {
        const val DEFAULT_BASE_COIN = "EUR"
    }
}