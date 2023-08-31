package com.housewar.flightsearch.presentation.flight_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.housewar.flightsearch.R
import com.housewar.flightsearch.navigation.NavigationDestination
import com.housewar.flightsearch.presentation.flight_list.view_model.FlightListViewModel

object FlightListScreenDestination : NavigationDestination {
    override val route = "flight_list"
    override val titleResourceId = R.string.flight_search
}

@Composable
fun FlightListScreenStateful(
    modifier: Modifier = Modifier
) {
    val viewModel: FlightListViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val flights by viewModel.flights.collectAsState()

    FlightListScreen(
        uiState = uiState,
        modifier = modifier,
        searchResults = searchResults,
        flights = flights,
        onEvent = viewModel::onEvent
    )
}