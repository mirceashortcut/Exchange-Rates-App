package com.example.exchangeratesapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.exchangeratesapp.ui.home.HomeScreen
import com.example.exchangeratesapp.ui.settings.SettingsScreen

@Composable
fun NavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable(route = "home") {
            HomeScreen(viewModel = hiltViewModel(), navController = navController)
        }

        composable(route = "settings") {
            val home = remember(it) { navController.getBackStackEntry("home") }

            SettingsScreen(viewModel = hiltViewModel(home), navController = navController)
        }
    }
}