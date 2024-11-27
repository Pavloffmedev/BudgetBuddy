package com.pavloffmedev.budgetbuddy.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.pavloffmedev.budgetbuddy.MainActivityFlag
import com.pavloffmedev.budgetbuddy.Urls
import com.pavloffmedev.budgetbuddy.objects.User
import kotlinx.serialization.json.Json
import java.util.HashMap

class MainActivityModel(
    private val requester: RequestQueue,
    private val saved: SharedPreferences
) : ViewModel() {

    private val mutableActivityFlag = MutableLiveData<MainActivityFlag>()
    val activityFlagLive: LiveData<MainActivityFlag> get() = mutableActivityFlag

    private val mutableAddWastesVisibility = MutableLiveData(false)
    val addWastesVisibilityLive: LiveData<Boolean> get() = mutableAddWastesVisibility

    private val mutableUser = MutableLiveData<User>()
    val userLive : LiveData<User> get() = mutableUser

    private val mutableLoadingVisibility = MutableLiveData(false)
    val loadingVisibilityLive : LiveData<Boolean> get() = mutableLoadingVisibility

    private val accessToken by lazy { saved.getString("access_token", "").toString() }

    init {
        if (!saved.contains("access_token")) {
            mutableActivityFlag.value = MainActivityFlag.NEED_TO_LOGIN
        }
        else {
            mutableLoadingVisibility.value = true
            getUserData()
        }
    }


    override fun onCleared() {
        super.onCleared()
        requester.cancelAll("MainActivityModel")
    }


    /**
     * При нажатии кнопки "Добавить траты"
     */
    fun changeAddWastesLayVisibility(state : Boolean) {
        mutableAddWastesVisibility.value = state
    }


    /**
     * Получить данные пользователя
     */
    private fun getUserData() {
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, Urls.GET_USER_DATA,
            {
                val decoder = Json { ignoreUnknownKeys = true }
                mutableUser.value = decoder.decodeFromString(it)

                mutableLoadingVisibility.value = false
            }, null
        ) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["access_token"] = accessToken
                return params
            }
        }
        requester.add(stringRequest).tag = "MainActivity"
    }


}