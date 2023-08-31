package com.housewar.flightsearch.domain.use_case

data class FlightSearchUseCases (
    val getAllAirports: GetAllAirports,
    val getSearchText: GetSearchText,
    val setSearchText: SetSearchText,
    val findAirports: FindAirports,
    val getFavoriteFlights: GetFavoriteFlights,
    val getFlightsByDepartureCode: GetFlightsByDepartureCode,
    val getAirportByIata: GetAirportByIata,
    val getFavorite: GetFavorite,
    val insertFavorite: InsertFavorite,
    val deleteFavorite: DeleteFavorite
)