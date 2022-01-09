package com.luthtan.eye_beacon_android.di

import android.content.Context
import com.google.gson.Gson
import com.luthtan.eye_beacon_android.MyApplication
import com.luthtan.eye_beacon_android.base.util.SessionHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): MyApplication {
        return app as MyApplication
    }

    @Provides
    @Singleton
    fun provideSessionHelper(myApplication: MyApplication, gson: Gson): SessionHelper {
        return SessionHelper(myApplication, gson)
    }

}