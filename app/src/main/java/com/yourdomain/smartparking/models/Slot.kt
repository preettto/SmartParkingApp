package com.yourdomain.smartparking.models

data class Slot(
    val id: String = "",
    val status: String = "available", // available, reserved, occupied
    val reservedBy: String? = null,
    val location: Pair<Int, Int>? = null // For blueprint map
) 