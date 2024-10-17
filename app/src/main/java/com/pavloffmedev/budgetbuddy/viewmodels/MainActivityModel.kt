package com.pavloffmedev.budgetbuddy.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.RequestQueue
import com.pavloffmedev.budgetbuddy.MainActivityFlag

class MainActivityModel(
    private val requester: RequestQueue,
    private val saved: SharedPreferences
) : ViewModel() {

    private val mutableActivityFlag = MutableLiveData<MainActivityFlag>()
    val activityFlagLive: LiveData<MainActivityFlag> get() = mutableActivityFlag

    init {

    }

    override fun onCleared() {
        super.onCleared()
        requester.cancelAll("MainActivityModel")
    }

}