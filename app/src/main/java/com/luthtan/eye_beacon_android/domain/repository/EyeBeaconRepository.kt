package com.luthtan.eye_beacon_android.domain.repository

import com.luthtan.eye_beacon_android.domain.entities.dashboard.BleModel
import io.reactivex.Flowable

interface EyeBeaconRepository {
    fun signInRoom(
        uuid: String = "",
        status: String = ""
    ): Flowable<BleModel>
}