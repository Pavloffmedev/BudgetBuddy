package com.pavloffmedev.budgetbuddy.`object`

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val name: String? = null,
    val monthMoneyRemaining: Int,
    val monthWastes: Int
)
