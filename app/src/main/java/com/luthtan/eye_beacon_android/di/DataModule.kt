package com.luthtan.eye_beacon_android.di

import android.content.Context
import androidx.room.Room
import com.luthtan.eye_beacon_android.BuildConfig
import com.luthtan.eye_beacon_android.data.local.room.DashboardDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideDashboardDB(@ApplicationContext context: Context): DashboardDB {
        return Room.databaseBuilder(context, DashboardDB::class.java, BuildConfig.APPLICATION_ID).build()
    }

    @Singleton
    @Provides
    fun provideDashboardDao(dashboardDB: DashboardDB) = dashboardDB.dashboardDao()
}