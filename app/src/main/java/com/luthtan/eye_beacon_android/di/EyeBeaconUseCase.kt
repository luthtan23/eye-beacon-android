package com.luthtan.eye_beacon_android.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object EyeBeaconUseCase {

    @Provides
    @ViewModelScoped
    fun provideDashboardRepository() {

    }
}