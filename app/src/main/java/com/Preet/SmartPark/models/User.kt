package com.Preet.SmartPark.models

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val reservations: List<String> = emptyList()
) 