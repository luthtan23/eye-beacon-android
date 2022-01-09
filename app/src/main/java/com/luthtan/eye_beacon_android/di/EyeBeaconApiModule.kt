package com.luthtan.eye_beacon_android.di

import com.luthtan.eye_beacon_android.data.datasource.EyeBeaconDataSourceImpl
import com.luthtan.eye_beacon_android.data.local.room.DashboardDao
import com.luthtan.eye_beacon_android.data.mapper.SignInRoomMapper
import com.luthtan.eye_beacon_android.data.remote.EyeBeaconApi
import com.luthtan.eye_beacon_android.data.repository.EyeBeaconRepositoryImpl
import com.luthtan.eye_beacon_android.domain.datasource.EyeBeaconDataSource
import com.luthtan.eye_beacon_android.domain.repository.EyeBeaconRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EyeBeaconApiModule {
    @Singleton
    @Provides
    fun provideEyeBeaconDataSource(eyeBeaconApi: EyeBeaconApi, dashboardDao: DashboardDao): EyeBeaconDataSource =
        EyeBeaconDataSourceImpl(
            eyeBeaconApi, dashboardDao, SignInRoomMapper()
        )

    @Singleton
    @Provides
    fun provideApolloRepository(source: EyeBeaconDataSource): EyeBeaconRepository =
        EyeBeaconRepositoryImpl(
            source
        )

    @Singleton
    @Provides
    fun provideEyeBeaconApi(retrofit: Retrofit): EyeBeaconApi = retrofit.create(
        EyeBeaconApi::class.java
    )
}