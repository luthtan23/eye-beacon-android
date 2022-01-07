package com.luthtan.eye_beacon_android.base.config

import android.content.Context
import com.luthtan.eye_beacon_android.base.util.SessionHelper
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object WebApiProvider {
    fun getRetrofit(
        url: String,
        context: Context,
        sessionHelper: SessionHelper,
        okHttpClientBuilder: OkHttpClient.Builder
    ): Retrofit = Retrofit
        .Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(
            okHttpClientBuilder
                .addInterceptor(
                    OAuthInterceptor(
                        OAuthInterceptor.BEARER,
                        sessionHelper
                    )
                )
                .build()
        )
        .build()
}