package com.housewar.flightsearch.domain.use_case

import com.housewar.flightsearch.domain.model.Flight
import com.housewar.flightsearch.domain.repository.AirportRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteFlights(
    private val repository: AirportRepository
) {
    operator fun invoke() : Flow<List<Flight>> {
        return repository.getFavoriteFlights()
    }
}