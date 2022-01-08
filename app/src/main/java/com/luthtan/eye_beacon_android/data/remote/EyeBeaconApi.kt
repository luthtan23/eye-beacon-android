package com.luthtan.eye_beacon_android.data.remote

import com.luthtan.eye_beacon_android.domain.response.dashboard.BleResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.POST

interface EyeBeaconApi {
    @POST
    fun signInRoom(): Flow<Response<BleResponse>>
}