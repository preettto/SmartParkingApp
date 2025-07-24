package com.yourdomain.smartparking.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.yourdomain.smartparking.utils.FirebaseUtils

class QRScanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startQRScan()
    }

    private fun startQRScan() {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("Scan QR Code")
        integrator.setCameraId(0)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null && result.contents != null) {
            val reservationId = result.contents
            // TODO: Fetch reservation, mark as started/ended, calculate charges
            Toast.makeText(this, "Scanned: $reservationId", Toast.LENGTH_SHORT).show()
            setResult(Activity.RESULT_OK, Intent().putExtra("reservationId", reservationId))
            finish()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
} 