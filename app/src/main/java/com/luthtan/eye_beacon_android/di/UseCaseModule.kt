package com.luthtan.eye_beacon_android.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.luthtan.eye_beacon_android.domain.AndroidUIThread
import com.luthtan.eye_beacon_android.domain.PostExecutionThread
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun providePostExecutionThread(): PostExecutionThread = AndroidUIThread()

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }
}