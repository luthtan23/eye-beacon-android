package com.luthtan.eye_beacon_android.data.datasource

import com.luthtan.eye_beacon_android.data.remote.EyeBeaconApi
import com.luthtan.eye_beacon_android.domain.datasource.EyeBeaconDataSource
import com.luthtan.eye_beacon_android.domain.response.dashboard.BleResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

@ExperimentalCoroutinesApi
class EyeBeaconDataSourceImpl @Inject constructor(
    private val eyeBeaconApi: EyeBeaconApi
) : EyeBeaconDataSource {

    override fun signInRoom(uuid: String, status: String): Flow<Response<BleResponse>> {
        return eyeBeaconApi.signInRoom()
    }

}