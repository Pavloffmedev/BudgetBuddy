package com.pavloffmedev.budgetbuddy.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.android.volley.RequestQueue

class LoginActivityModel(
    private val requester: RequestQueue,
    private val saved: SharedPreferences
) : ViewModel() {

    init {

    }

    override fun onCleared() {
        super.onCleared()
        requester.cancelAll("LoginActivityModel")
    }
}