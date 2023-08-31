package com.housewar.flightsearch.data.repository

import com.housewar.flightsearch.data.database.AirportDao
import com.housewar.flightsearch.domain.model.Airport
import com.housewar.flightsearch.domain.model.Favorite
import com.housewar.flightsearch.domain.model.Flight
import com.housewar.flightsearch.domain.repository.AirportRepository
import kotlinx.coroutines.flow.Flow

class AirportRepositoryImp(
    private val airportDao: AirportDao
) : AirportRepository {

    override fun getAllAirportsStream(): Flow<List<Airport>> = airportDao.getAllAirportStream()

    override fun findAirports(searchText: String): Flow<List<Airport>> = airportDao.findAirports(searchText)

    override fun getFavoriteFlights(): Flow<List<Flight>> = airportDao.getFavoriteFlights()

    override fun getFlightsByDepartureCode(departure_code: String): Flow<List<Flight>> = airportDao.getFlightsByDepartureCode(departure_code)

    override fun getAirportByIata(iata_code: String): Flow<Airport> = airportDao.getAirportByIata(iata_code)

    override fun getFavorite(id: Int): Flow<Favorite> = airportDao.getFavorite(id)

    override suspend fun deleteFavorite(favorite: Favorite) = airportDao.deleteFavorite(favorite)

    override suspend fun insertFavorite(favorite: Favorite) = airportDao.insertFavorite(favorite)
}