package com.housewar.flightsearch.presentation.flight_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.housewar.flightsearch.R
import com.housewar.flightsearch.domain.model.Favorite
import com.housewar.flightsearch.domain.model.Flight

@Composable
fun FlightCard(
    flight: Flight,
    modifier: Modifier = Modifier,
    setFavorite: (Flight) -> Unit = {},
    isFavorite: Boolean = false
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(topEnd = 24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(16.dp),
            //verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = modifier
                    .weight(5f)
                    .fillMaxHeight()
            ) {
                AirportInformation(
                    airport_iata = flight.departure_code,
                    airport_name = flight.departure_name
                )
                Spacer(modifier = Modifier.height(8.dp))
                AirportInformation(
                    airport_iata = flight.destination_code,
                    airport_name = flight.destination_name
                )
            }
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                IconButton(
                    onClick = {
                        setFavorite(flight)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Search",
                        modifier = Modifier
                            .size(36.dp),
                        tint = if (isFavorite) MaterialTheme.colorScheme.primary else Color.Unspecified
                    )
                }
            }
        }
    }
}

@Composable
fun AirportInformation(
    airport_iata: String,
    airport_name: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.depart),
            style = MaterialTheme.typography.bodySmall
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = airport_iata,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = airport_name,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FlightCardPreview() {
    FlightCard(
        flight = Flight(
            favorite_id = 0,
            departure_code = "ORD",
            departure_name = "O'Hare International Airport",
            destination_code = "LAX",
            destination_name = "Los Angeles International Airport"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun AirportInformationPreview() {
    AirportInformation(
        airport_iata = "ORD",
        airport_name = "O'Hare International Airport"
    )
}