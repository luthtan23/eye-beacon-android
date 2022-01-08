package com.luthtan.eye_beacon_android.di

import com.luthtan.eye_beacon_android.domain.datasource.EyeBeaconDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EyeBeaconApiModule {
//    @Singleton
//    @Provides
//    fun provideApolloRepository(source: EyeBeaconDataSource): Home01Repository =
//        Home01RepositoryImpl(
//            source
//        )
//
//    @Singleton
//    @Provides
//    fun provideHome01Api(retrofit: Retrofit): Home01Api = retrofit.create(
//        Home01Api::class.java
//    )
}