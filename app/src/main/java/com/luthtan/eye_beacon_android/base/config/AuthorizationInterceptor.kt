package com.luthtan.eye_beacon_android.base.config

import android.content.Context
import com.luthtan.eye_beacon_android.base.util.SessionHelper
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(val context: Context, private val sessionHelper: SessionHelper) : Interceptor {
    val apiKey by lazy { "da2-dxbxnsi3jrb3bmclnru2vdowey" }
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = sessionHelper.getString(SessionHelper.ACCESS_TOKEN_KEY)

        return if(accessToken != null && accessToken.isNotEmpty()){
            val request = chain.request().newBuilder()
                .addHeader("Authorization", sessionHelper.getString(SessionHelper.ACCESS_TOKEN_KEY) ?: "")
    //            .addHeader("Authorization", AWSMobileClient.getInstance().tokens.accessToken.tokenString ?: "")
                .build()

            chain.proceed(request)
        }else{
            val request = chain.request().newBuilder()
                .addHeader("X-API-Key", apiKey)
                .build()

            chain.proceed(request)
        }
    }
}