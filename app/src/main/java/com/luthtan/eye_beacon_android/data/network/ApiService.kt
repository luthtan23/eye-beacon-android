package com.luthtan.eye_beacon_android.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST
    suspend fun getBleUser(@Body eyeBeaconUser: String): Response<String>

}