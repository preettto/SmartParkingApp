package com.Preet.SmartPark.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.Preet.SmartPark.adapters.SlotGrid
import com.Preet.SmartPark.models.Slot
import com.Preet.SmartPark.ui.theme.SmartParkingAppTheme
import com.Preet.SmartPark.utils.FirebaseUtils

class ParkingMapActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartParkingAppTheme {
                ParkingMapScreen()
            }
        }
    }
}

@Composable
fun ParkingMapScreen() {
    var slots by remember { mutableStateOf<List<Slot>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Fetch slots from Firebase
    LaunchedEffect(Unit) {
        FirebaseUtils.db.collection("slots")
            .get()
            .addOnSuccessListener { snapshot ->
                val slotList = snapshot.documents.mapNotNull { it.toObject(Slot::class.java) }
                slots = slotList
                isLoading = false
            }
            .addOnFailureListener {
                isLoading = false
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Parking Map",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            SlotGrid(
                slots = slots,
                onSlotClick = { /* Handle slot click if needed */ },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}