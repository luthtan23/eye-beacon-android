package com.luthtan.eye_beacon_android.domain.interactor

import com.luthtan.eye_beacon_android.domain.PostExecutionThread
import com.luthtan.eye_beacon_android.domain.baseusecase.rxjava.FlowableUseCase
import com.luthtan.eye_beacon_android.domain.entities.dashboard.BleModel
import com.luthtan.eye_beacon_android.domain.repository.EyeBeaconRepository
import io.reactivex.Flowable

class SignInRoom(
    private val eyeBeaconRepository: EyeBeaconRepository,
    postExecutionThread: PostExecutionThread
): FlowableUseCase<BleModel, SignInRoom.Param>(postExecutionThread) {

    override fun build(params: Param): Flowable<BleModel> {
        return eyeBeaconRepository.signInRoom("", "")
    }

    class Param()

}