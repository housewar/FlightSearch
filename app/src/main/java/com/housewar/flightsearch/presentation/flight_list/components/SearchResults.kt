package com.housewar.flightsearch.presentation.flight_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.housewar.flightsearch.data.preview_data.PreviewAirportList
import com.housewar.flightsearch.domain.model.Airport

@Composable
fun SearchResults(
    searchResults: List<Airport>,
    selectAirport: (Airport) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(searchResults) { airport ->
            TextButton(onClick = { selectAirport(airport) }) {
                Text(
                    text = airport.iata_code,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = airport.name)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchResultsPreview(){
    SearchResults(
        searchResults = PreviewAirportList.getAirportList(),
        selectAirport = {}
    )
}