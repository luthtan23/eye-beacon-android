package com.luthtan.eye_beacon_android.data.network

import com.luthtan.simplebleproject.data.network.ApiConstant
import com.luthtan.simplebleproject.domain.response.dashboard.BleResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(ApiConstant.DASHBOARD_URL)
    suspend fun getBleUser(): Response<BleResponse>

}