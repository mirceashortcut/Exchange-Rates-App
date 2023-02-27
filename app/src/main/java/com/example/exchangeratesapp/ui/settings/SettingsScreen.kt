package com.example.exchangeratesapp.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exchangeratesapp.R
import com.example.exchangeratesapp.ui.theme.ExchangeRatesAppTheme

@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    SettingsScreen(uiState = uiState, uiEvents = viewModel.uiEvents)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SettingsScreen(
    modifier: Modifier = Modifier,
    uiState: SettingsViewModel.UiState,
    uiEvents: SettingsViewModel.UiEvents
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.settings_screen)) },
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Go Back")
                }
            }
        )

        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(end = 10.dp),
                text = stringResource(id = R.string.settings_preferred_coin)
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(value = uiState.selectedCurrency,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(text = stringResource(id = R.string.currency)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
                )

                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    uiState.currenciesSymbols.forEach { selectedOption ->
                        DropdownMenuItem(onClick = {
                            uiEvents.onCurrencyChanged(selectedOption)
                            expanded = false
                        }) {
                            Text(text = selectedOption)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    ExchangeRatesAppTheme {
        SettingsScreen(
            uiState = SettingsViewModel.UiState(),
            uiEvents = SettingsViewModel.UiEvents()
        )
    }
}