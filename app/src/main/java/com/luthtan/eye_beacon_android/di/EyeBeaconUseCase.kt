package com.luthtan.eye_beacon_android.di

import com.luthtan.eye_beacon_android.domain.PostExecutionThread
import com.luthtan.eye_beacon_android.domain.interactor.GetHistoryList
import com.luthtan.eye_beacon_android.domain.interactor.InsertHistory
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

    @Provides
    @ViewModelScoped
    fun provideInsertHistory(
        eyeBeaconRepository: EyeBeaconRepository,
        postExecutionThread: PostExecutionThread
    ) : InsertHistory {
        return InsertHistory(eyeBeaconRepository, postExecutionThread)
    }

    @Provides
    @ViewModelScoped
    fun provideGetHistoryList(
        eyeBeaconRepository: EyeBeaconRepository,
        postExecutionThread: PostExecutionThread
    ) : GetHistoryList {
        return GetHistoryList(eyeBeaconRepository, postExecutionThread)
    }
}