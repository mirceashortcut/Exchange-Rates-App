package com.example.exchangeratesapp.ui.home

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
class HomeViewModel @Inject constructor(
    private val repository: RatesRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val response = repository.getExchangeRatesEur(BASE_COIN).rates

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