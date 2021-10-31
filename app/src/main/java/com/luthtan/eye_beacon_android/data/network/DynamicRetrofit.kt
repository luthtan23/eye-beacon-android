package com.luthtan.eye_beacon_android.data.network

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DynamicRetrofit(private val gson: Gson) {

    private fun buildClient() = OkHttpClient.Builder()
        .build()

    private var baseUrl = "http://192.168.0.107:5001//eyebeacon/dashboard/status/" //default url

    private fun buildApi() = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(buildClient())
        .build().create(ApiService::class.java)

    var api: ApiService = buildApi()
        private set

    fun setUrl(url: String) {
        if (baseUrl != url)
            baseUrl = url

        api = buildApi()
    }
}