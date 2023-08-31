package com.housewar.flightsearch.domain.use_case

import com.housewar.flightsearch.data.preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetSearchText(
    private val repository: UserPreferencesRepository
) {
    operator fun invoke() : Flow<String> {
        return repository.userPreferencesFlow.map{
            it.searchText
        }
    }
}