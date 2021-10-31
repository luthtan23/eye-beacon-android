package com.luthtan.eye_beacon_android.data.network

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okio.IOException

class HostSelectionInterceptor() :
    Interceptor {
    @Volatile
    private var host: HttpUrl? = null

    fun setHost(host: String) {
        this.host = host.toHttpUrlOrNull()
    }

    fun clear() {
        host = null
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        host?.let {
            val newUrl = request.url.newBuilder()
                .scheme(it.scheme)
                .host(it.toUrl().toURI().host)
                .port(it.port)
                .build()
            request = request.newBuilder().url(newUrl).build()
        }
        return chain.proceed(request)
    }
}