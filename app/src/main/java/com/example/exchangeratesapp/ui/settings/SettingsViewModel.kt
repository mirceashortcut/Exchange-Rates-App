package com.example.exchangeratesapp.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangeratesapp.domain.repository.RatesRepository
import com.example.exchangeratesapp.ui.home.HomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: RatesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    val uiEvents = UiEvents(
        onCurrencyChanged = ::onCurrencyChanged
    )

    init {
        viewModelScope.launch {
            val response = repository.getExchangeRatesEur(HomeViewModel.BASE_COIN).rates.keys

            for (symbol in response) {
                _uiState.update {
                    it.copy(
                        currenciesSymbols = it.currenciesSymbols + listOf(symbol)
                    )
                }
            }
        }
    }

    private fun onCurrencyChanged(newCurrency: String) {
        _uiState.update {
            it.copy(selectedCurrency = newCurrency)
        }
    }

    data class UiState(
        val currenciesSymbols: List<String> = emptyList(),
        // The currently selected currency should either come from navigation arguments (home screen)
        // or from the database
        val selectedCurrency: String = HomeViewModel.BASE_COIN
    )

    data class UiEvents(
        val onCurrencyChanged: (String) -> Unit = {}
    )
}