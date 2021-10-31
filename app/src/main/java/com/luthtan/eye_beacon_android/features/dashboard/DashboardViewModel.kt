package com.luthtan.eye_beacon_android.features.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luthtan.eye_beacon_android.data.network.ApiResponse
import com.luthtan.eye_beacon_android.data.repository.dashboard.DashboardRepository
import com.luthtan.eye_beacon_android.domain.entities.dashboard.BleEntity
import com.luthtan.eye_beacon_android.domain.entities.login.LoginPage
import com.luthtan.eye_beacon_android.domain.response.dashboard.BleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class DashboardViewModel : ViewModel(),
    KoinComponent {

    private val dashboardRepository: DashboardRepository by inject()

    private val _getAllData = MutableLiveData<List<BleEntity>>()
    val getAllData: LiveData<List<BleEntity>> = _getAllData

    private val _getUserData = MutableLiveData<String>()
    val getUserData: LiveData<String> = _getUserData

    init {
        _getUserData.value = ""
    }

    fun testParams() {
        _getUserData.value = dashboardRepository.testParams().value
    }

    fun setParams(params: LoginPage) {
        _getUserData.value = dashboardRepository.getUserData(params).value?.body!!
    }

    fun insertRealtime(bleEntity: BleEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            dashboardRepository.insertUserData(bleEntity)
        }
    }

    private fun getAllUserData(): LiveData<List<BleEntity>> = dashboardRepository.getAllUserData()




    init {
        _getAllData.value = getAllUserData().value
    }
}