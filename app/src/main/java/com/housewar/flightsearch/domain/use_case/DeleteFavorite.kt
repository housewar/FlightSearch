package com.housewar.flightsearch.domain.use_case

import com.housewar.flightsearch.domain.model.Favorite
import com.housewar.flightsearch.domain.repository.AirportRepository

class DeleteFavorite(
    private val repository: AirportRepository
) {
    suspend operator fun invoke(favorite: Favorite) {
        return repository.deleteFavorite(favorite)
    }
}