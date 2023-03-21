package com.example.exchangeratesapp.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.exchangeratesapp.R
import com.example.exchangeratesapp.ui.common.SharedViewModel
import com.example.exchangeratesapp.ui.theme.ExchangeRatesAppTheme

@Composable
fun SettingsScreen(viewModel: SharedViewModel, navController: NavHostController) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
//    val currencyState by viewModel.selectedCurrency.collectAsStateWithLifecycle()
//    val refreshTimeState by viewModel.selectedRefreshTime.collectAsStateWithLifecycle()

    SettingsScreen(
        uiState = uiState,
//        currencyState = currencyState,
//        refreshTimeState = refreshTimeState,
        uiEvents = viewModel.uiEvents,
        navController = navController
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SettingsScreen(
    modifier: Modifier = Modifier,
    uiState: SharedViewModel.UiState,
//    currencyState: String,
//    refreshTimeState: Int,
    uiEvents: SharedViewModel.UiEvents,
    navController: NavHostController
) {
    var expandedCoin by remember {
        mutableStateOf(false)
    }

    var expandedRefresh by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.settings_screen)) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Go Back")
                }
            }
        )

//        Row(
//            modifier = Modifier.padding(10.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(
//                modifier = Modifier.padding(end = 10.dp),
//                text = stringResource(id = R.string.settings_preferred_coin)
//            )
//
//            ExposedDropdownMenuBox(
//                expanded = expandedCoin,
//                onExpandedChange = { expandedCoin = !expandedCoin }
//            ) {
//                TextField(value = currencyState,
//                    onValueChange = {},
//                    readOnly = true,
//                    label = { Text(text = stringResource(id = R.string.currency)) },
//                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCoin) }
//                )
//
//                ExposedDropdownMenu(
//                    expanded = expandedCoin,
//                    onDismissRequest = { expandedCoin = false }) {
//                    uiState.currencies.forEach { selectedOption ->
//                        DropdownMenuItem(onClick = {
//                            uiEvents.onCurrencyChanged(selectedOption.symbol)
//                            expandedCoin = false
//                        }) {
//                            Text(text = selectedOption.symbol)
//                        }
//                    }
//                }
//            }
//        }
//
//        Row(
//            modifier = Modifier.padding(10.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(
//                modifier = Modifier.padding(end = 10.dp),
//                text = stringResource(id = R.string.settings_refresh_time)
//            )
//
//            ExposedDropdownMenuBox(
//                expanded = expandedRefresh,
//                onExpandedChange = { expandedRefresh = !expandedRefresh }
//            ) {
//                TextField(value = refreshTimeState.toString(),
//                    onValueChange = {},
//                    readOnly = true,
//                    label = { Text(text = stringResource(id = R.string.currency)) },
//                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedRefresh) }
//                )
//
//                ExposedDropdownMenu(
//                    expanded = expandedRefresh,
//                    onDismissRequest = { expandedRefresh = false }) {
//                    SharedViewModel.REFRESH_TIMES_IN_MINUTES.forEach { selectedOption ->
//                        DropdownMenuItem(onClick = {
//                            uiEvents.onRefreshTimeChanged(selectedOption)
//                            expandedRefresh = false
//                        }) {
//                            Text(text = selectedOption.toString())
//                        }
//                    }
//                }
//            }
//        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    ExchangeRatesAppTheme {
        SettingsScreen(
            uiState = SharedViewModel.UiState(),
//            currencyState = "EUR",
//            refreshTimeState = 1,
            uiEvents = SharedViewModel.UiEvents(),
            navController = NavHostController(LocalContext.current)
        )
    }
}