package com.housewar.flightsearch.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.housewar.flightsearch.presentation.flight_list.FlightListScreen
import com.housewar.flightsearch.presentation.flight_list.FlightListScreenDestination
import com.housewar.flightsearch.presentation.flight_list.FlightListScreenStateful

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = FlightListScreenDestination.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // Note List
        composable(route = FlightListScreenDestination.route) {
            FlightListScreenStateful()
        }
    }
}