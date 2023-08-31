package com.housewar.flightsearch.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.housewar.flightsearch.domain.model.Airport
import com.housewar.flightsearch.domain.model.Favorite
import com.housewar.flightsearch.domain.model.Flight
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query(
        """
        SELECT * from favorite
        WHERE id = :id
    """
    )
    fun getFavorite(id: Int): Flow<Favorite>

    @Query(
        """
        SELECT * from airport 
    """
    )
    fun getAllAirportStream(): Flow<List<Airport>>

    @Query(
        """
        SELECT * from airport
        WHERE airport.iata_code LIKE :searchText
        OR airport.name LIKE :searchText
    """
    )
    fun findAirports(searchText: String): Flow<List<Airport>>

    @Query(
        """
        SELECT * from airport
        WHERE airport.iata_code = :iata_code
    """
    )
    fun getAirportByIata(iata_code: String): Flow<Airport>

    /*
    * The favorite departure_code and destination_code are joined with the airport table
    * to get the airport names. This join requires two instances of the airport table using aliases to work correctly.
    * */
    @Query(
        """
        SELECT
            favorite.id as favorite_id,
            depart_airport.iata_code as departure_code, depart_airport.name as departure_name,
            dest_airport.iata_code as destination_code, dest_airport.name as destination_name
        FROM favorite
        INNER JOIN airport as depart_airport ON favorite.departure_code = depart_airport.iata_code
        INNER JOIN airport as dest_airport ON favorite.destination_code = dest_airport.iata_code 
    """
    )
    fun getFavoriteFlights(): Flow<List<Flight>>

    /*
    * The airport table is joined with itself to creates all possible combinations
    * of departure/destination airports where the departure code is not equal to the destination code.
    * Additionally, the favorite table is left joined so that the favorite_id is populated for favorites
    * and is null value for non-favorites.
    * */
    @Query(
        """
        SELECT
            favorite.id as favorite_id,
            depart_airport.iata_code as departure_code, depart_airport.name as departure_name,
            dest_airport.iata_code as destination_code, dest_airport.name as destination_name
        FROM airport as depart_airport, airport as dest_airport
        LEFT JOIN favorite ON (favorite.departure_code = depart_airport.iata_code AND favorite.destination_code = dest_airport.iata_code)
        WHERE (depart_airport.iata_code = :departure_code   
        AND depart_airport.iata_code <> dest_airport.iata_code)
    """
    )
    fun getFlightsByDepartureCode(departure_code: String): Flow<List<Flight>>
}