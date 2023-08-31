package com.housewar.flightsearch.domain.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/*
* Class entity for the airport table.
* */
@Entity(tableName = "airport")
data class Airport (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val iata_code: String,
    val name: String,
    val passengers: Int
)