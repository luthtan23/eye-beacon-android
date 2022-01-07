package com.luthtan.eye_beacon_android.base.config

import com.luthtan.eye_beacon_android.base.util.SessionHelper
import okhttp3.Interceptor

class OAuthInterceptor(private val tokenType: String, private val sessionHelper: SessionHelper) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        return if (!sessionHelper.getString(SessionHelper.ACCESS_TOKEN_KEY).isNullOrEmpty()) {
            request =
                request.newBuilder()
                    .header("Authorization", "$tokenType ${sessionHelper.getString(SessionHelper.ACCESS_TOKEN_KEY)}")
                    .build()

            chain.proceed(request)
        } else {
            chain.proceed(request)
        }

    }

    companion object {
        const val BEARER = "Bearer"
    }
}