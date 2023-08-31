package com.housewar.flightsearch.domain.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/*
* Class entity for the favorite table
* */
@Entity(tableName = "favorite")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val departure_code: String,
    val destination_code: String
)
