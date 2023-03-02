package com.example.exchangeratesapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.exchangeratesapp.R
import com.example.exchangeratesapp.ui.common.SharedViewModel
import com.example.exchangeratesapp.ui.theme.ExchangeRatesAppTheme

@Composable
fun HomeScreen(viewModel: SharedViewModel, navController: NavHostController) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currencyState by viewModel.selectedCurrency.collectAsStateWithLifecycle()

    HomeScreen(uiState = uiState, currencyState = currencyState, navController = navController)
}

@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: SharedViewModel.UiState,
    currencyState: String,
    navController: NavHostController
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
        ) {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.home_screen)) },
                actions = {
                    IconButton(
                        onClick = { navController.navigate("settings") }
                    ) {
                        Icon(imageVector = Icons.Filled.Settings, contentDescription = "Settings")
                    }
                }
            )

            Text(
                modifier = Modifier.padding(10.dp),
                text = stringResource(id = R.string.exchange_rates_title, currencyState),
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.padding(10.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 5.dp)
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

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (uiState.isLoading)
                CircularProgressIndicator()
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
        HomeScreen(
            uiState = SharedViewModel.UiState(),
            currencyState = "EUR",
            navController = NavHostController(context = LocalContext.current)
        )
    }
}