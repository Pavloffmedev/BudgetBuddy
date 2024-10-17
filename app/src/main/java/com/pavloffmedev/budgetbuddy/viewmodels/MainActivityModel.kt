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
        if (!saved.contains("access_token")) {
            mutableActivityFlag.value = MainActivityFlag.NEED_TO_LOGIN
        }
        else {

        }
    }


    override fun onCleared() {
        super.onCleared()
        requester.cancelAll("MainActivityModel")
    }


}