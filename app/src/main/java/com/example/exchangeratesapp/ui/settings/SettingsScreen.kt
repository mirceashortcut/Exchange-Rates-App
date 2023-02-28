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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.exchangeratesapp.R
import com.example.exchangeratesapp.ui.common.SharedViewModel
import com.example.exchangeratesapp.ui.theme.ExchangeRatesAppTheme

@Composable
fun SettingsScreen(viewModel: SharedViewModel, navController: NavHostController) {
    val uiState by viewModel.uiState.collectAsState()

    SettingsScreen(uiState = uiState, uiEvents = viewModel.uiEvents, navController = navController)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SettingsScreen(
    modifier: Modifier = Modifier,
    uiState: SharedViewModel.UiState,
    uiEvents: SharedViewModel.UiEvents,
    navController: NavHostController
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
                IconButton(onClick = { navController.popBackStack() }) {
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
                    uiState.currencies.forEach { selectedOption ->
                        DropdownMenuItem(onClick = {
                            uiEvents.onCurrencyChanged(selectedOption.symbol)
                            expanded = false
                        }) {
                            Text(text = selectedOption.symbol)
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
            uiState = SharedViewModel.UiState(),
            uiEvents = SharedViewModel.UiEvents(),
            navController = NavHostController(LocalContext.current)
        )
    }
}