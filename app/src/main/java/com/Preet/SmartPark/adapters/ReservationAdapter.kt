package com.Preet.SmartPark.adapters

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.Preet.SmartPark.models.Reservation

@Composable
fun ReservationList(
    reservations: List<Reservation>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(reservations) { reservation ->
            ReservationItem(reservation = reservation)
        }
    }
}

@Composable
fun ReservationItem(
    reservation: Reservation,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Reservation ID: ${reservation.id}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Slot: ${reservation.slotId}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Status: ${reservation.status}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}