package com.housewar.flightsearch.domain.use_case

import com.housewar.flightsearch.domain.model.Airport
import com.housewar.flightsearch.domain.repository.AirportRepository
import kotlinx.coroutines.flow.Flow

class GetAllAirports(
    private val repository: AirportRepository
) {
    operator fun invoke() : Flow<List<Airport>> {
        return repository.getAllAirportsStream()
    }
}