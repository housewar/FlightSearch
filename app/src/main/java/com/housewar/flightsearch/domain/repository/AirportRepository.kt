package com.housewar.flightsearch.domain.repository

import com.housewar.flightsearch.domain.model.Airport
import com.housewar.flightsearch.domain.model.Favorite
import com.housewar.flightsearch.domain.model.Flight
import kotlinx.coroutines.flow.Flow

interface AirportRepository {

    fun getAllAirportsStream() : Flow<List<Airport>>

    fun findAirports(searchText: String) : Flow<List<Airport>>

    fun getFavoriteFlights() : Flow<List<Flight>>

    fun getFlightsByDepartureCode(departure_code: String) : Flow<List<Flight>>

    fun getAirportByIata(iata_code: String) : Flow<Airport>

    fun getFavorite(id: Int): Flow<Favorite>

    suspend fun deleteFavorite(favorite: Favorite)

    suspend fun insertFavorite(favorite: Favorite)
}