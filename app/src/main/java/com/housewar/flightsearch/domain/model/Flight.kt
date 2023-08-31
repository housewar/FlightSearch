package com.housewar.flightsearch.domain.model

/*
* Class for a flight showing departure airport, destination airport,
* and an id if the flight is a favorite.
*
* I would like to have embedded these classes, but this would cause column name duplications.
*
* Consider renaming the id's to reflect the table name, i.e. favorite_id, airport_id.
* */
data class Flight(
    val favorite_id: Int?,
    val departure_code: String,
    val departure_name: String,
    val destination_code: String,
    val destination_name: String,
)