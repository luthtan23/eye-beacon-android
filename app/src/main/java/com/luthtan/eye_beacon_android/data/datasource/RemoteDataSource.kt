package com.luthtan.eye_beacon_android.data.datasource

import androidx.lifecycle.MutableLiveData
import com.luthtan.eye_beacon_android.data.network.ApiResponse
import com.luthtan.eye_beacon_android.data.network.ApiService
import com.luthtan.eye_beacon_android.domain.entities.login.LoginPage
import com.luthtan.eye_beacon_android.domain.response.dashboard.BleResponse
import kotlinx.coroutines.*
import java.lang.Exception

class RemoteDataSource(private val apiService: ApiService) {

    fun getUserData(params: String): MutableLiveData<ApiResponse<String>> {
        val bleResponse = MutableLiveData<ApiResponse<String>>()
        GlobalScope.launch {
            try {
                val request = apiService.getBleUser(params)
                if (request.isSuccessful) {
                    if (request.body() != null) {
                        bleResponse.value = ApiResponse.success(request.body()!!.toString())
                    } else {
                        bleResponse.value = ApiResponse.success("EMPTY RESPONSE")
                    }
                } else {
                    bleResponse.value = ApiResponse.success(request.message().toString())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                bleResponse.value = ApiResponse.success(e.toString())
            }
        }
        return bleResponse
    }
}