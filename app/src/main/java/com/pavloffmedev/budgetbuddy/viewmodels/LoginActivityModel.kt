package com.pavloffmedev.budgetbuddy.viewmodels

import android.content.SharedPreferences
import android.util.Log
import androidx.core.widget.ListViewAutoScrollHelper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.pavloffmedev.budgetbuddy.LoginActivityFlags
import com.pavloffmedev.budgetbuddy.Urls
import org.json.JSONObject
import java.util.HashMap

class LoginActivityModel(
    private val requester: RequestQueue,
    private val saved: SharedPreferences
) : ViewModel() {

    private val mutableIsEmailLoading = MutableLiveData(false)
    val isEmailLoading: LiveData<Boolean> get() = mutableIsEmailLoading

    private val mutableIsCodeLoading = MutableLiveData(false)
    val isCodeLoading: LiveData<Boolean> get() = mutableIsCodeLoading

    private val mutableActivityFlags = MutableLiveData<LoginActivityFlags>()
    val activityFlags: LiveData<LoginActivityFlags> get() = mutableActivityFlags

    var email = ""
    private var code = ""

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
        this.email = email.trim()

        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, Urls.SEND_CODE,
            {
                val data = JSONObject(it)
                if (data["result"] == "success") {
                    mutableActivityFlags.value = LoginActivityFlags.CODE_NEED
                }

                mutableIsEmailLoading.value = false
            }, null
        ) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["email"] = this@LoginActivityModel.email
                return params
            }
        }
        requester.add(stringRequest).tag = "MainActivity"
    }


    /**
     * Проверка кода подтверждения
     */
    fun confirmCode(code : String) {
        mutableIsCodeLoading.value = true
        this.code = code.trim()

        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, Urls.CONFIRM_CODE,
            {
                val data = JSONObject(it)
                if (data["result"] == "success") {
                    saved.edit().putString("access_token", data.getString("access_token")).apply()
                    mutableActivityFlags.value = LoginActivityFlags.SUCCESS_AUTH
                }
                else if (data.has("error") && data["error"] == "email or code is invalid") {
                    mutableActivityFlags.value = LoginActivityFlags.EMAIL_NEED
                }
                else {
                    mutableActivityFlags.value = LoginActivityFlags.INVALID_CODE
                }

                mutableIsCodeLoading.value = false
            }, null
        ) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["email"] = this@LoginActivityModel.email
                params["code"] = this@LoginActivityModel.code
                return params
            }
        }
        requester.add(stringRequest).tag = "MainActivity"
    }
}