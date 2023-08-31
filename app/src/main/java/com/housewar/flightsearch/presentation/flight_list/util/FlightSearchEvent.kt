package com.housewar.flightsearch.presentation.flight_list.util

import com.housewar.flightsearch.domain.model.Airport
import com.housewar.flightsearch.domain.model.Flight

sealed class FlightSearchEvent{
    data class UpdateSearchField(val text: String): FlightSearchEvent()
    data class SelectDepartAirport(val airport: Airport): FlightSearchEvent()
    data class SetFavorite(val flight: Flight): FlightSearchEvent()
}
