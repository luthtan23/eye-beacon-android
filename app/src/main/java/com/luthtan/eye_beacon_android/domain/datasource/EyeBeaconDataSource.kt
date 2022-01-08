package com.luthtan.eye_beacon_android.domain.datasource

import com.luthtan.eye_beacon_android.domain.response.dashboard.BleResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface EyeBeaconDataSource {
    fun signInRoom(
        uuid: String = "",
        status: String = ""
    ): Flow<Response<BleResponse>>
}