package com.pavloffmedev.budgetbuddy.viewmodels

import android.content.SharedPreferences
import androidx.core.widget.ListViewAutoScrollHelper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.RequestQueue
import com.pavloffmedev.budgetbuddy.LoginActivityFlags

class LoginActivityModel(
    private val requester: RequestQueue,
    private val saved: SharedPreferences
) : ViewModel() {

    private val mutableIsEmailLoading = MutableLiveData<Boolean>()
    val isEmailLoading: LiveData<Boolean> get() = mutableIsEmailLoading

    private val mutableIsCodeLoading = MutableLiveData<Boolean>()
    val isCodeLoading: LiveData<Boolean> get() = mutableIsCodeLoading

    private val mutableActivityFlags = MutableLiveData<LoginActivityFlags>()
    val activityFlags: LiveData<LoginActivityFlags> get() = mutableActivityFlags

    init {
        mutableActivityFlags.value = LoginActivityFlags.EMAIL_NEED
    }

    override fun onCleared() {
        super.onCleared()
        requester.cancelAll("LoginActivityModel")
    }


    /**
     * Отправка кода подтверждения на указанную почту
     */
    fun sendCode(email : String) {
        mutableIsEmailLoading.value = true
        mutableActivityFlags.value = LoginActivityFlags.CODE_NEED

    }
}