package com.housewar.flightsearch.presentation.flight_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.housewar.flightsearch.R
import com.housewar.flightsearch.data.preview_data.PreviewAirportList
import com.housewar.flightsearch.domain.model.Airport
import com.housewar.flightsearch.domain.model.Flight
import com.housewar.flightsearch.presentation.flight_list.components.FlightCard
import com.housewar.flightsearch.presentation.flight_list.components.SearchResults
import com.housewar.flightsearch.presentation.flight_list.util.FlightSearchEvent
import com.housewar.flightsearch.presentation.flight_list.view_model.FlightListUiState

@Composable
fun FlightListScreen(
    uiState: FlightListUiState,
    modifier: Modifier = Modifier,
    searchResults: List<Airport> = listOf(),
    flights: List<Flight> = listOf(),
    onEvent: (FlightSearchEvent) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TextField(
                value = uiState.searchText,
                onValueChange = {
                    onEvent(FlightSearchEvent.UpdateSearchField(it))
                },
                modifier = modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = stringResource(R.string.search_flights))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        modifier = Modifier.size(24.dp)
                    )
                },
                maxLines = 1
            )
        }
    ) { topBarPadding ->
        Box(
            modifier = Modifier.padding(topBarPadding)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                items(flights) {flight ->
                    FlightCard(
                        flight = flight,
                        setFavorite = { onEvent(FlightSearchEvent.SetFavorite(it))},
                        isFavorite = (flight.favorite_id ?: 0) > 0
                    )
                }
            }
            if (uiState.isSearchActive) {
                Card(
                    shape = RectangleShape,
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    SearchResults(
                        searchResults = searchResults,
                        selectAirport = { onEvent(FlightSearchEvent.SelectDepartAirport(it)) }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FlightListPreview() {
    val airports = PreviewAirportList.getAirportList()
    FlightListScreen(
        FlightListUiState()
    )
}