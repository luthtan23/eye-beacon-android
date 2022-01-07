package com.luthtan.eye_beacon_android.data.repository.dashboard

import androidx.lifecycle.LiveData
import com.luthtan.eye_beacon_android.data.network.ApiResponse
import com.luthtan.eye_beacon_android.domain.entities.dashboard.BleEntity
import com.luthtan.eye_beacon_android.domain.entities.login.LoginPage
import com.luthtan.eye_beacon_android.domain.response.dashboard.BleResponse

interface DashboardRepositorySource {

    fun getUserData(params: String): LiveData<ApiResponse<String>>

    suspend fun insertUserData(bleEntity: BleEntity)

    fun getAllUserData(): LiveData<List<BleEntity>>

}