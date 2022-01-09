package com.luthtan.eye_beacon_android.domain.interactor

import com.luthtan.eye_beacon_android.domain.PostExecutionThread
import com.luthtan.eye_beacon_android.domain.baseusecase.rxjava.FlowableUseCase
import com.luthtan.eye_beacon_android.domain.entities.dashboard.BleEntity
import com.luthtan.eye_beacon_android.domain.repository.EyeBeaconRepository
import io.reactivex.Flowable

class InsertHistory(
    private val eyeBeaconRepository: EyeBeaconRepository,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<Long, InsertHistory.Param>(postExecutionThread) {

    override fun build(params: Param): Flowable<Long> {
        return Flowable.just(eyeBeaconRepository.insertHistory(params.bleEntity))
    }

    class Param(val bleEntity: BleEntity)
}