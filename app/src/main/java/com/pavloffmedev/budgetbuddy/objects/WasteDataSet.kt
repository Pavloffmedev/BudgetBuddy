package com.pavloffmedev.budgetbuddy.objects

import kotlinx.serialization.Serializable

@Serializable
data class WasteDataSet(
    val index: Float,
    val name: String
)
