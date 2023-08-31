package com.housewar.flightsearch.domain.use_case

import com.housewar.flightsearch.domain.model.Flight
import com.housewar.flightsearch.domain.repository.AirportRepository
import kotlinx.coroutines.flow.Flow

class GetFlightsByDepartureCode(
    private val repository: AirportRepository
) {
    operator fun invoke(departure_code: String) : Flow<List<Flight>> {
        return repository.getFlightsByDepartureCode(departure_code = departure_code)
    }
}