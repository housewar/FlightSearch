package com.housewar.flightsearch.presentation.flight_list.view_model

import com.housewar.flightsearch.domain.model.Airport

/*
* These are independent user-determined values, or respond directly to user input.
* */
data class FlightListUiState(
    val searchText: String = "",
    val isSearchActive: Boolean = false,
    val departAirport: Airport? = null
)