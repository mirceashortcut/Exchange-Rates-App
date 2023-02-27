package com.example.exchangeratesapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exchangeratesapp.R
import com.example.exchangeratesapp.ui.theme.ExchangeRatesAppTheme

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    HomeScreen(uiState = uiState)
}

@Composable
private fun HomeScreen(modifier: Modifier = Modifier, uiState: HomeViewModel.UiState) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.home_screen)) },
            actions = {
                IconButton(
                    onClick = {}
                ) {
                    Icon(imageVector = Icons.Filled.Settings, contentDescription = "Settings")
                }
            }
        )

        Text(
            modifier = Modifier.padding(10.dp),
            text = stringResource(id = R.string.exchange_rates_title, HomeViewModel.BASE_COIN),
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.padding(10.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            contentPadding = PaddingValues(vertical = 5.dp)
        ) {
            items(uiState.currencies) {
                ExchangeCurrency(
                    modifier = Modifier,
                    symbol = it.symbol,
                    value = it.value
                )
            }
        }
    }
}

@Composable
private fun ExchangeCurrency(modifier: Modifier, symbol: String, value: Float) {
    Row(modifier = modifier) {
        Text(text = "$symbol - ")
        Text(text = value.toString())
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ExchangeRatesAppTheme {
        HomeScreen(uiState = HomeViewModel.UiState())
    }
}