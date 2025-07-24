package com.Preet.SmartPark.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.Preet.SmartPark.ui.theme.SmartParkingAppTheme
import com.Preet.SmartPark.utils.UPIPaymentUtils

class ExitActivity : ComponentActivity() {
    private val upiId = "yourupi@bank" // Replace with your UPI ID
    private val name = "Smart Parking"
    private val note = "Parking Charges"
    private var amount = "90" // Example amount, set dynamically

    private val paymentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "Payment Success!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Payment Failed or Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartParkingAppTheme {
                ExitScreen(
                    amount = amount,
                    onPayClick = {
                        UPIPaymentUtils.payWithUPI(amount, upiId, name, note, this)
                    }
                )
            }
        }
    }
}

@Composable
fun ExitScreen(
    amount: String,
    onPayClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Exit Parking",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Total Charge: ₹$amount",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Button(
                    onClick = onPayClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Pay with UPI")
                }
            }
        }
    }
}