package com.housewar.flightsearch.domain.use_case

import com.housewar.flightsearch.domain.model.Favorite
import com.housewar.flightsearch.domain.repository.AirportRepository
import kotlinx.coroutines.flow.Flow

class GetFavorite(
    private val repository: AirportRepository
) {
    operator fun invoke(id: Int) : Flow<Favorite> {
        return repository.getFavorite(id)
    }
}