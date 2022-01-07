package com.luthtan.eye_beacon_android.data.repository.dashboard

import androidx.lifecycle.LiveData
import com.luthtan.eye_beacon_android.data.datasource.LocalDataSource
import com.luthtan.eye_beacon_android.data.utils.AppExecutors
import com.luthtan.eye_beacon_android.domain.entities.dashboard.BleEntity

class DashboardRepository (
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : DashboardRepositorySource {

    override suspend fun insertUserData(bleEntity: BleEntity) = localDataSource.insertUserData(bleEntity)

    override fun getAllUserData(): LiveData<List<BleEntity>> = localDataSource.getAllUserData()

}