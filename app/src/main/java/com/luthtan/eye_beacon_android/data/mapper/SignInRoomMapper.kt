package com.luthtan.eye_beacon_android.data.mapper

import com.google.gson.Gson
import com.luthtan.eye_beacon_android.domain.entities.dashboard.BleModel
import com.luthtan.eye_beacon_android.domain.entities.dashboard.StatusModel
import com.luthtan.eye_beacon_android.domain.response.dashboard.BleResponse

class SignInRoomMapper : Mapper<BleResponse, BleModel>() {
    override fun apply(from: BleResponse): BleModel {
        return BleModel(status = Gson().fromJson(Gson().toJson(from.status), StatusModel::class.java))
    }
}