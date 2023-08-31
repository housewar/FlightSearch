package com.housewar.flightsearch.data.preview_data

import com.housewar.flightsearch.domain.model.Airport

object PreviewAirportList {
    private val airports: List<Airport> = listOf(
        Airport(
            id = 0,
            iata_code = "ORD",
            name = "O'Hare International Airport",
            passengers = 0
        ),
        Airport(
            id = 1,
            iata_code = "LAX",
            name = "Los Angelos International Airport",
            passengers = 0
        ),
        Airport(
            id = 2,
            iata_code = "MDW",
            name = "Chicago Midway International Airport",
            passengers = 0
        ),
        Airport(
            id = 3,
            iata_code = "DCA",
            name = "Ronald Reagan Washington National Airport",
            passengers = 0
        )
    )
fun getAirportList(): List<Airport> {
    return airports
}
}