package com.luthtan.eye_beacon_android.features.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luthtan.simplebleproject.data.network.ApiResponse
import com.luthtan.eye_beacon_android.data.repository.dashboard.DashboardRepository
import com.luthtan.simplebleproject.domain.entities.dashboard.BleEntity
import com.luthtan.simplebleproject.domain.response.dashboard.BleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

class DashboardViewModel(private val dashboardRepository: DashboardRepository) : ViewModel(),
    KoinComponent {

    fun getUserData(): LiveData<ApiResponse<BleResponse>> = dashboardRepository.getUserData()

    fun insertRealtime(bleEntity: BleEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            dashboardRepository.insertUserData(bleEntity)
        }
    }

    private fun getAllUserData(): LiveData<List<BleEntity>> = dashboardRepository.getAllUserData()

    private val _getAllData = MutableLiveData<List<BleEntity>>()
    val getAllData: LiveData<List<BleEntity>> = _getAllData


    init {
        _getAllData.value = getAllUserData().value
    }
}