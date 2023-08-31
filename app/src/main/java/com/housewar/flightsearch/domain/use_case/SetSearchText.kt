package com.housewar.flightsearch.domain.use_case

import com.housewar.flightsearch.data.preferences.UserPreferencesRepository

class SetSearchText(
    private val repository: UserPreferencesRepository
) {
    suspend operator fun invoke(searchText: String) {
        repository.setSearchText(searchText)
    }
}