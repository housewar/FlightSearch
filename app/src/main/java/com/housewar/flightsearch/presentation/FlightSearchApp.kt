package com.housewar.flightsearch.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.housewar.flightsearch.navigation.NavigationGraph
import com.housewar.flightsearch.presentation.flight_list.FlightListScreenDestination

@Composable
fun FlightSearchApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = FlightListScreenDestination.route
){
    NavigationGraph(
        navController = navController,
        startDestination = startDestination
    )
}