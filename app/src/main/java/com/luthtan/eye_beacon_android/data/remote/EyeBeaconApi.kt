package com.luthtan.eye_beacon_android.data.remote

import com.luthtan.eye_beacon_android.domain.response.dashboard.BleResponse
import io.reactivex.Flowable
import retrofit2.http.POST

interface EyeBeaconApi {
    @POST("sample/post/json")
    fun signInRoom(): Flowable<BleResponse>
}