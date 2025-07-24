package com.Preet.SmartPark.models

data class Reservation(
    val id: String = "",
    val userId: String = "",
    val slotId: String = "",
    val startTime: Long = 0L,
    val endTime: Long = 0L,
    val status: String = "active", // active, completed, cancelled
    val slipUrl: String? = null
) 