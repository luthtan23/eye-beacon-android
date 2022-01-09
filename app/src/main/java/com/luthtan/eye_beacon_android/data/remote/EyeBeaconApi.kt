package com.luthtan.eye_beacon_android.data.remote

import com.luthtan.eye_beacon_android.domain.dtos.BleBody
import com.luthtan.eye_beacon_android.domain.response.dashboard.BleResponse
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface EyeBeaconApi {
    @POST
    fun signInRoom(
        @Url baseUrl: String,
        @Body bleBody: BleBody
    ): Flowable<BleResponse>
}