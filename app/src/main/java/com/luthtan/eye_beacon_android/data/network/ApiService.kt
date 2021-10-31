package com.luthtan.eye_beacon_android.data.network

import com.luthtan.eye_beacon_android.domain.entities.login.LoginPage
import com.luthtan.eye_beacon_android.domain.response.dashboard.BleResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST(ApiConstant.ROOM)
    suspend fun getBleUser(@Body eyeBeaconUser: LoginPage): Response<String>

}