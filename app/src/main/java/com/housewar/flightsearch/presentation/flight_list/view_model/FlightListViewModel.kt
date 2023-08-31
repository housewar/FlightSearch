package com.housewar.flightsearch.presentation.flight_list.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.housewar.flightsearch.domain.model.Airport
import com.housewar.flightsearch.domain.model.Favorite
import com.housewar.flightsearch.domain.model.Flight
import com.housewar.flightsearch.domain.use_case.FlightSearchUseCases
import com.housewar.flightsearch.presentation.flight_list.util.FlightSearchEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightListViewModel @Inject constructor(
    // All domain logic occurs in flightSearchUseCases
    private val flightSearchUseCases: FlightSearchUseCases
) : ViewModel() {

    /*
    * Independent user-determined state variables: Search Text, is Search Active, selected Departure Airport
    * */
    private val _uiState = MutableStateFlow(FlightListUiState())
    val uiState: StateFlow<FlightListUiState> = _uiState

    /*
    * Search text is saved in user preferences. If it's non-blank:
    * Retrieve the search text
    * Set the departure airport
    * Set the main screen to display the selected flights flow.
    * */
    init {
        flightSearchUseCases.getSearchText().map { searchText ->
            if (searchText != "") {
                _uiState.update { state ->
                    state.copy(
                        searchText = searchText,
                        departAirport = flightSearchUseCases.getAirportByIata(searchText).first()
                    )
                }
                flights = selectedFlights
            }
        }.launchIn(viewModelScope)
    }

    /*
    * When state is updated, re-query the auto-complete search results.
    * Auto-complete uses a LIKE query against the iata_code and name,
    * so text is surrounded by % wildcards.
    * */
    val searchResults: StateFlow<List<Airport>> =
        _uiState.map {
            flightSearchUseCases.findAirports("%${it.searchText}%").first()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = listOf()
        )

    /*
    * Whenever changes are made to favorites or airports, re-query favorite flights.
    * */
    private val favoriteFlights: StateFlow<List<Flight>> =
        flightSearchUseCases.getFavoriteFlights().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = listOf()
        )

    /*
    * State changes as well as updates to the favorite flight query will update
    * the selected flights flow.
    * It's necessary to include favorites to ensure the favorite icon responds insertions and deletions.
    * */
    private val selectedFlights: StateFlow<List<Flight>> =
        _uiState.combine(favoriteFlights) { state, favorites ->
            flightSearchUseCases.getFlightsByDepartureCode(
                departure_code = state.departAirport?.iata_code ?: ""
            ).first()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = listOf()
        )

    /*
    * This is a pointer variable. The main screen either displays favorite or selected flights.
    * The flights variable points to the appropriate flow depending on user action.
    * */
    var flights: StateFlow<List<Flight>> = favoriteFlights
        private set

    /*
    * User events that update state
    * */
    fun onEvent(event: FlightSearchEvent) {
        when (event) {
            /*
            * Update the search field with user input.
            * The search box should only show when the search text is non-blank
            * Search text is only saved to user preferences when it matches
            * a departure code, or is blank.
            * If search text is blank, the main screen should display favorite flights.
            * */
            is FlightSearchEvent.UpdateSearchField -> {
                _uiState.update { state ->
                    state.copy(
                        searchText = event.text,
                        isSearchActive = event.text.isNotBlank()
                    )
                }
                if (_uiState.value.searchText.isBlank()) {
                    viewModelScope.launch {
                        flightSearchUseCases.setSearchText("")
                    }
                    flights = favoriteFlights
                }
            }
            /*
            * Set the departure airport.
            * Remove the search results box.
            * Save the search text to preferences
            * Set the main screen to display selected flights instead of favorites.
            * */
            is FlightSearchEvent.SelectDepartAirport -> {
                _uiState.update { state ->
                    state.copy(
                        searchText = event.airport.iata_code,
                        isSearchActive = false,
                        departAirport = event.airport
                    )
                }
                viewModelScope.launch {
                    flightSearchUseCases.setSearchText(event.airport.iata_code)
                }
                flights = selectedFlights
            }
            /*
            * Insert/delete the current flight into/from the favorite table.
            * If favorite_id is null, the favorite needs to be inserted.
            * If it is not null, the favorite needed to be deleted.
            * */
            is FlightSearchEvent.SetFavorite -> {
                val favorite = Favorite(
                    id = event.flight.favorite_id ?: 0,
                    departure_code = event.flight.departure_code,
                    destination_code = event.flight.destination_code
                )
                viewModelScope.launch {
                    if (favorite.id > 0) {
                        flightSearchUseCases.deleteFavorite(favorite)
                    } else {
                        flightSearchUseCases.insertFavorite(favorite)
                    }
                }
            }
        }
    }
}