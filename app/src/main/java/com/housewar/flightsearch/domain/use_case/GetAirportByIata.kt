package com.housewar.flightsearch.domain.use_case

import com.housewar.flightsearch.domain.model.Airport
import com.housewar.flightsearch.domain.repository.AirportRepository
import kotlinx.coroutines.flow.Flow

class GetAirportByIata(
    private val repository: AirportRepository
) {
    operator fun invoke(iata_code: String) : Flow<Airport> {
        return repository.getAirportByIata(iata_code)
    }
}