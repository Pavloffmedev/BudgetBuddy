package com.pavloffmedev.budgetbuddy.objects

import kotlinx.serialization.Serializable

@Serializable
data class Waste(
    val categoryName: String,
    val sum: Int,
    val time: String,
)