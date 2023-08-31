package com.housewar.flightsearch.domain.use_case

import com.housewar.flightsearch.domain.model.Airport
import com.housewar.flightsearch.domain.repository.AirportRepository
import kotlinx.coroutines.flow.Flow

class FindAirports(
    private val repository: AirportRepository
) {
    operator fun invoke(searchText: String) : Flow<List<Airport>> {
        return repository.findAirports(searchText)
    }
}