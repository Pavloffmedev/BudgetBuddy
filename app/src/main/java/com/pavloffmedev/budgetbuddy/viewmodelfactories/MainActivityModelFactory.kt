package com.pavloffmedev.budgetbuddy.viewmodelfactories

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.volley.toolbox.Volley
import com.pavloffmedev.budgetbuddy.Keys
import com.pavloffmedev.budgetbuddy.viewmodels.MainActivityModel

class MainActivityModelFactory(context: Context) : ViewModelProvider.Factory {
    private val sharedPrefs: SharedPreferences = context.getSharedPreferences(
        Keys.DATA_STORE_KEY,
        Context.MODE_PRIVATE
    )
    private val requester = Volley.newRequestQueue(context)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityModel(saved = sharedPrefs, requester = requester) as T
    }
}