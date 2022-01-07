package com.luthtan.eye_beacon_android.data.repository.dashboard

import androidx.lifecycle.LiveData
import com.luthtan.eye_beacon_android.domain.entities.dashboard.BleEntity

interface DashboardRepositorySource {

    suspend fun insertUserData(bleEntity: BleEntity)

    fun getAllUserData(): LiveData<List<BleEntity>>

}