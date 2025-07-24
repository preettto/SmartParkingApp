package com.Preet.SmartPark.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.Preet.SmartPark.adapters.ReservationList
import com.Preet.SmartPark.adapters.SlotGrid
import com.Preet.SmartPark.models.Reservation
import com.Preet.SmartPark.models.Slot
import com.Preet.SmartPark.ui.theme.SmartParkingAppTheme
import com.Preet.SmartPark.utils.FirebaseUtils

class AdminActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartParkingAppTheme {
                AdminScreen()
            }
        }
    }
}

@Composable
fun AdminScreen() {
    var slots by remember { mutableStateOf<List<Slot>>(emptyList()) }
    var reservations by remember { mutableStateOf<List<Reservation>>(emptyList()) }
    var totalRevenue by remember { mutableStateOf(0) }
    val context = LocalContext.current

    // Fetch data from Firebase
    LaunchedEffect(Unit) {
        FirebaseUtils.db.collection("slots")
            .addSnapshotListener { snapshot, _ ->
                val slotList = mutableListOf<Slot>()
                snapshot?.forEach { doc ->
                    slotList.add(doc.toObject(Slot::class.java))
                }
                slots = slotList
            }

        FirebaseUtils.db.collection("reservations")
            .addSnapshotListener { snapshot, _ ->
                val reservationList = mutableListOf<Reservation>()
                snapshot?.forEach { doc ->
                    reservationList.add(doc.toObject(Reservation::class.java))
                }
                reservations = reservationList
            }

        // Fetch revenue
        FirebaseUtils.db.collection("reservations")
            .whereEqualTo("status", "completed")
            .get()
            .addOnSuccessListener { snapshot ->
                var revenue = 0
                for (doc in snapshot.documents) {
                    val start = doc.getLong("startTime") ?: 0L
                    val end = doc.getLong("endTime") ?: 0L
                    revenue += calculateCharges(start, end)
                }
                totalRevenue = revenue
            }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Admin Dashboard",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        item {
            Card {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Total Revenue: ₹$totalRevenue",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Button(
                        onClick = {
                            val newSlotId = (slots.size + 1).toString()
                            val newSlot = Slot(
                                id = newSlotId,
                                status = "available",
                                reservedBy = null,
                                location = Pair(1, slots.size + 1)
                            )
                            FirebaseUtils.db.collection("slots").document(newSlotId).set(newSlot)
                        },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("Add New Slot")
                    }
                }
            }
        }

        item {
            Text(
                text = "Parking Slots",
                style = MaterialTheme.typography.titleLarge
            )
        }

        item {
            SlotGrid(
                slots = slots,
                onSlotClick = { },
                onSlotLongClick = { slot ->
                    FirebaseUtils.db.collection("slots").document(slot.id).delete()
                },
                modifier = Modifier.height(200.dp)
            )
        }

        item {
            Text(
                text = "Reservations",
                style = MaterialTheme.typography.titleLarge
            )
        }

        item {
            ReservationList(
                reservations = reservations,
                modifier = Modifier.height(300.dp)
            )
        }
    }
}

private fun calculateCharges(startTime: Long, endTime: Long): Int {
    val durationHours = ((endTime - startTime) / (1000 * 60 * 60)).toInt().coerceAtLeast(1)
    return durationHours * 30 // ₹30 per hour
}