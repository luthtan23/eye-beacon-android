package com.luthtan.eye_beacon_android.data.datasource

import androidx.lifecycle.LiveData
import com.luthtan.simplebleproject.data.local.room.DashboardDao
import com.luthtan.eye_beacon_android.domain.entities.dashboard.BleEntity

class LocalDataSource(private val dashboardDao: DashboardDao) {

    fun getAllUserData(): LiveData<List<BleEntity>> = dashboardDao.getAllUserData()

    suspend fun insertUserData(bleEntity: BleEntity) = dashboardDao.insertUserData(bleEntity)
}