package com.yourdomain.smartparking.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yourdomain.smartparking.R
import com.yourdomain.smartparking.utils.UPIPaymentUtils

class ExitActivity : AppCompatActivity() {
    private val upiId = "yourupi@bank" // Replace with your UPI ID
    private val name = "Smart Parking"
    private val note = "Parking Charges"
    private var amount = "90" // Example amount, set dynamically

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exit)

        val chargeText = findViewById<TextView>(R.id.chargeText)
        val payBtn = findViewById<Button>(R.id.payBtn)
        chargeText.text = "Total Charge: ₹$amount"

        payBtn.setOnClickListener {
            UPIPaymentUtils.payWithUPI(amount, upiId, name, note, this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1001) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Payment Success!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Payment Failed or Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
} 