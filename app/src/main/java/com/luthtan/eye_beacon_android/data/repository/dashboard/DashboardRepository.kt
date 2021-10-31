package com.luthtan.eye_beacon_android.data.repository.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.luthtan.eye_beacon_android.data.network.ApiResponse
import com.luthtan.eye_beacon_android.data.datasource.LocalDataSource
import com.luthtan.eye_beacon_android.data.datasource.RemoteDataSource
import com.luthtan.eye_beacon_android.data.utils.AppExecutors
import com.luthtan.eye_beacon_android.domain.entities.dashboard.BleEntity
import com.luthtan.eye_beacon_android.domain.entities.login.LoginPage
import com.luthtan.eye_beacon_android.domain.response.dashboard.BleResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class DashboardRepository (
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val appExecutors: AppExecutors
) : DashboardRepositorySource {

    override fun getUserData(params: LoginPage): LiveData<ApiResponse<String>> = remoteDataSource.getUserData(params)

    override suspend fun insertUserData(bleEntity: BleEntity) = localDataSource.insertUserData(bleEntity)

    override fun getAllUserData(): LiveData<List<BleEntity>> = localDataSource.getAllUserData()

    override fun testParams(): LiveData<String> {
        val bleResponse = MutableLiveData<ApiResponse<String>>()
        bleResponse.postValue(ApiResponse.success("TEST REPOSITORY"))
        val test = MutableLiveData<String>()
        test.value = "TEST REPOSITORY"
        return test
    }
}