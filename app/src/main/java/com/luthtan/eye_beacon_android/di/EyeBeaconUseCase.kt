package com.luthtan.eye_beacon_android.di

import com.luthtan.eye_beacon_android.domain.PostExecutionThread
import com.luthtan.eye_beacon_android.domain.interactor.SignInRoom
import com.luthtan.eye_beacon_android.domain.repository.EyeBeaconRepository
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
    fun provideSignInRoom(
        eyeBeaconRepository: EyeBeaconRepository,
        postExecutionThread: PostExecutionThread
    ) : SignInRoom {
        return SignInRoom(eyeBeaconRepository, postExecutionThread)
    }
}