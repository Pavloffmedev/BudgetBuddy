package com.pavloffmedev.budgetbuddy.objects

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val name: String? = null,
    val monthMoneyRemaining: Int,
    val monthWastes: Int,
    val monthStats: ArrayList<WasteDataSet>? = null
)