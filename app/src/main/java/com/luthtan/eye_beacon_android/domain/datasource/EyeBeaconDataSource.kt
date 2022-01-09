package com.luthtan.eye_beacon_android.domain.datasource

import com.luthtan.eye_beacon_android.domain.entities.dashboard.BleEntity
import com.luthtan.eye_beacon_android.domain.entities.dashboard.BleModel
import io.reactivex.Flowable

interface EyeBeaconDataSource {
    fun signInRoom(
        uuid: String = "",
        status: String = ""
    ): Flowable<BleModel>

    fun getHistoryList(): Flowable<List<BleEntity>>

    fun insertHistory(bleEntity: BleEntity): Long
}