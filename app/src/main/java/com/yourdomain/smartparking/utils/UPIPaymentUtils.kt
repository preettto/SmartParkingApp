package com.yourdomain.smartparking.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri

object UPIPaymentUtils {
    fun payWithUPI(amount: String, upiId: String, name: String, note: String, activity: Activity) {
        val uri = Uri.parse("upi://pay").buildUpon()
            .appendQueryParameter("pa", upiId)
            .appendQueryParameter("pn", name)
            .appendQueryParameter("tn", note)
            .appendQueryParameter("am", amount)
            .appendQueryParameter("cu", "INR")
            .build()
        val intent = Intent(Intent.ACTION_VIEW, uri)
        val chooser = Intent.createChooser(intent, "Pay with UPI")
        activity.startActivityForResult(chooser, 1001)
    }
} 