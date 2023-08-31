package com.housewar.flightsearch.di

import android.app.Application
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.housewar.flightsearch.data.database.FlightDatabase
import com.housewar.flightsearch.data.preferences.UserPreferencesRepository
import com.housewar.flightsearch.data.repository.AirportRepositoryImp
import com.housewar.flightsearch.domain.repository.AirportRepository
import com.housewar.flightsearch.domain.use_case.DeleteFavorite
import com.housewar.flightsearch.domain.use_case.FindAirports
import com.housewar.flightsearch.domain.use_case.FlightSearchUseCases
import com.housewar.flightsearch.domain.use_case.GetAirportByIata
import com.housewar.flightsearch.domain.use_case.GetAllAirports
import com.housewar.flightsearch.domain.use_case.GetFavorite
import com.housewar.flightsearch.domain.use_case.GetFavoriteFlights
import com.housewar.flightsearch.domain.use_case.GetFlightsByDepartureCode
import com.housewar.flightsearch.domain.use_case.GetSearchText
import com.housewar.flightsearch.domain.use_case.InsertFavorite
import com.housewar.flightsearch.domain.use_case.SetSearchText
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

private const val USER_PREFERENCES = "user_preferences"
private const val FLIGHT_DATABASE = "flight_database"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFlightDatabase(app: Application): FlightDatabase {
        return Room.databaseBuilder(
            app,
            FlightDatabase::class.java,
            FLIGHT_DATABASE)
            .createFromAsset("database/flight_search.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideAirportRepository(db: FlightDatabase) : AirportRepository {
        return AirportRepositoryImp(db.airportDao())
    }

    @Provides
    @Singleton
    fun providePreferencesRepository(app: Application): UserPreferencesRepository {
        return UserPreferencesRepository(
            PreferenceDataStoreFactory.create(
                corruptionHandler = ReplaceFileCorruptionHandler(
                    produceNewData = { emptyPreferences() }
                ),
                migrations = listOf(SharedPreferencesMigration(app, USER_PREFERENCES)),
                scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
                produceFile = {app.preferencesDataStoreFile(USER_PREFERENCES)}
        ))
    }

    @Provides
    @Singleton
    fun provideFlightSearchUseCases(
        airportRepository: AirportRepository,
        preferencesRepository: UserPreferencesRepository
    ) : FlightSearchUseCases {
        return FlightSearchUseCases(
            GetAllAirports(airportRepository),
            GetSearchText(preferencesRepository),
            SetSearchText(preferencesRepository),
            FindAirports(airportRepository),
            GetFavoriteFlights(airportRepository),
            GetFlightsByDepartureCode(airportRepository),
            GetAirportByIata(airportRepository),
            GetFavorite(airportRepository),
            InsertFavorite(airportRepository),
            DeleteFavorite(airportRepository)
        )
    }
}