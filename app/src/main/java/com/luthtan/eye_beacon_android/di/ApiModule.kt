package com.luthtan.eye_beacon_android.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.luthtan.eye_beacon_android.MyApplication
import com.luthtan.eye_beacon_android.base.config.WebApiProvider
import com.luthtan.eye_beacon_android.base.util.SessionHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Singleton
    @Provides
    fun provideWebApiProvider(): WebApiProvider = WebApiProvider

    @Singleton
    @Provides
    fun provideOkhttpBuilder(
        myApplication: MyApplication,
    ): OkHttpClient.Builder = OkHttpClient().newBuilder()
        .addInterceptor(ChuckerInterceptor.Builder(myApplication.applicationContext).build())
        .addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(20, TimeUnit.SECONDS)

    @Singleton
    @Provides
    fun provideRetrofit(
        webApiProvider: WebApiProvider,
        myApplication: MyApplication,
        sessionHelper: SessionHelper,
        okHttpClientBuilder: OkHttpClient.Builder
    ): Retrofit = webApiProvider
        .getRetrofit(
            myApplication.getBaseUrl(),
            myApplication.applicationContext,
            sessionHelper, okHttpClientBuilder
        )
}