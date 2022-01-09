package com.luthtan.eye_beacon_android.data.repository

import com.luthtan.eye_beacon_android.domain.datasource.EyeBeaconDataSource
import com.luthtan.eye_beacon_android.domain.dtos.BleBody
import com.luthtan.eye_beacon_android.domain.entities.dashboard.BleEntity
import com.luthtan.eye_beacon_android.domain.entities.dashboard.BleModel
import com.luthtan.eye_beacon_android.domain.repository.EyeBeaconRepository
import io.reactivex.Flowable
import javax.inject.Inject

class EyeBeaconRepositoryImpl @Inject constructor(
    private val source: EyeBeaconDataSource
) : EyeBeaconRepository {
    override fun signInRoom(uuid: String, status: BleBody): Flowable<BleModel> {
        return source.signInRoom(uuid, status)
    }

    override fun getHistoryList(): Flowable<List<BleEntity>> {
        return source.getHistoryList()
    }

    override fun insertHistory(bleEntity: BleEntity) = source.insertHistory(bleEntity)
}