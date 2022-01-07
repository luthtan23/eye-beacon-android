package com.luthtan.eye_beacon_android.features.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luthtan.eye_beacon_android.data.repository.dashboard.DashboardRepository
import com.luthtan.eye_beacon_android.domain.entities.dashboard.BleEntity
import com.luthtan.eye_beacon_android.domain.entities.login.LoginPage
import com.luthtan.eye_beacon_android.domain.response.dashboard.BleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

//    private val dashboardRepository: DashboardRepository by inject()

    private val _getAllData = MutableLiveData<List<BleEntity>>()
    val getAllData: LiveData<List<BleEntity>> = _getAllData

    private val _getUserData = MutableLiveData<String>()
    val getUserData: LiveData<String> = _getUserData

    init {
        _getUserData.value = ""
    }

//    fun setParams(params: LoginPage) {
//        _getUserData.value = dashboardRepository.getUserData(params.localIP).value?.body!!
//    }
//
//    fun insertRealtime(bleEntity: BleEntity) {
//        viewModelScope.launch(Dispatchers.IO) {
//            dashboardRepository.insertUserData(bleEntity)
//        }
//    }

//    private fun getAllUserData(): LiveData<List<BleEntity>> = dashboardRepository.getAllUserData()

    init {
//        _getAllData.value = getAllUserData().value
    }
}