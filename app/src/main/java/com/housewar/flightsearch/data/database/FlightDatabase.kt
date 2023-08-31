package com.housewar.flightsearch.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.housewar.flightsearch.domain.model.Airport
import com.housewar.flightsearch.domain.model.Favorite


@Database(
    entities =[Airport::class, Favorite::class],
    version = 1,
    exportSchema = false
)
abstract class FlightDatabase : RoomDatabase() {

    abstract fun airportDao(): AirportDao

    companion object {
        const val DATABASE_NAME = "flight_database"
    }

}