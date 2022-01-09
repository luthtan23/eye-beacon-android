package com.luthtan.eye_beacon_android.domain.interactor

import com.luthtan.eye_beacon_android.domain.PostExecutionThread
import com.luthtan.eye_beacon_android.domain.baseusecase.rxjava.FlowableUseCase
import com.luthtan.eye_beacon_android.domain.entities.dashboard.BleEntity
import com.luthtan.eye_beacon_android.domain.repository.EyeBeaconRepository
import io.reactivex.Flowable

class GetHistoryList(
    private val eyeBeaconRepository: EyeBeaconRepository,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<List<BleEntity>, Void?>(postExecutionThread) {

    override fun build(params: Void?): Flowable<List<BleEntity>> {
        return eyeBeaconRepository.getHistoryList()
    }
}