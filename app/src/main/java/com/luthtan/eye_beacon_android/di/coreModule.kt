package com.luthtan.eye_beacon_android.di

import android.content.Context
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.luthtan.eye_beacon_android.data.local.preferences.PreferenceConstants
import com.luthtan.simplebleproject.data.local.preferences.PreferenceHelper
import com.luthtan.simplebleproject.data.repository.PreferencesRepository
import com.luthtan.eye_beacon_android.data.network.ApiConstant
import com.luthtan.eye_beacon_android.data.network.ApiService
import com.luthtan.eye_beacon_android.data.network.DynamicRetrofit
import com.luthtan.eye_beacon_android.data.network.HostSelectionInterceptor
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val coreModule = module {

    single<Gson> {
        val builder =
            GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        builder.setLenient().create()
    }

    single {
        PreferenceHelper(
            (androidApplication()).getSharedPreferences(
                PreferenceConstants.PREFERENCE_NAME,
                Context.MODE_PRIVATE
            )
        )
    }

    single {
        FirebaseDatabase.getInstance().reference
    }

    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(loggingInterceptor)
        httpClient.connectTimeout(ApiConstant.API_TIME_OUT, TimeUnit.MILLISECONDS)
        httpClient.addInterceptor { chain ->
            val request = chain.request().newBuilder().build()
            chain.proceed(request)
        }
        val okHttpClient = httpClient.build()

        val builder = OkHttpClient().newBuilder()
        builder.interceptors().forEach { builder.addInterceptor(it) }

        Retrofit.Builder()
            .baseUrl(ApiConstant.BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(get() as Gson))
            .build()
    }

    single {
        (get<Retrofit>()).create(ApiService::class.java)
    }


}