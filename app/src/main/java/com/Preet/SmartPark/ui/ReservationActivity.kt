package com.Preet.SmartPark.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.Preet.SmartPark.adapters.SlotGrid
import com.Preet.SmartPark.models.Slot
import com.Preet.SmartPark.ui.theme.SmartParkingAppTheme
import com.Preet.SmartPark.utils.FirebaseUtils

class ReservationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartParkingAppTheme {
                ReservationScreen()
            }
        }
    }
}

@Composable
fun ReservationScreen() {
    var slots by remember { mutableStateOf<List<Slot>>(emptyList()) }
    val context = LocalContext.current

    // Fetch slots from Firebase
    LaunchedEffect(Unit) {
        FirebaseUtils.db.collection("slots")
            .addSnapshotListener { snapshot, _ ->
                val slotList = mutableListOf<Slot>()
                snapshot?.forEach { doc ->
                    slotList.add(doc.toObject(Slot::class.java))
                }
                slots = slotList
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Available Parking Slots",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (slots.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            SlotGrid(
                slots = slots,
                onSlotClick = { slot ->
                    reserveSlot(slot, context)
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

private fun reserveSlot(slot: Slot, context: android.content.Context) {
    if (slot.status != "available") {
        Toast.makeText(context, "Slot is not available", Toast.LENGTH_SHORT).show()
        return
    }
    
    val userId = FirebaseUtils.auth.currentUser?.uid ?: return
    FirebaseUtils.db.collection("slots").document(slot.id)
        .update(mapOf("status" to "reserved", "reservedBy" to userId))
        .addOnSuccessListener {
            Toast.makeText(context, "Slot reserved!", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener {
            Toast.makeText(context, "Reservation failed", Toast.LENGTH_SHORT).show()
        }
}