package com.luthtan.eye_beacon_android.data.datasource

import com.luthtan.eye_beacon_android.data.local.room.DashboardDao
import com.luthtan.eye_beacon_android.data.mapper.SignInRoomMapper
import com.luthtan.eye_beacon_android.data.remote.EyeBeaconApi
import com.luthtan.eye_beacon_android.domain.datasource.EyeBeaconDataSource
import com.luthtan.eye_beacon_android.domain.dtos.BleBody
import com.luthtan.eye_beacon_android.domain.entities.dashboard.BleEntity
import com.luthtan.eye_beacon_android.domain.entities.dashboard.BleModel
import io.reactivex.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class EyeBeaconDataSourceImpl @Inject constructor(
    private val eyeBeaconApi: EyeBeaconApi,
    private val dashboardDao: DashboardDao,
    private val signInRoomMapper: SignInRoomMapper
) : EyeBeaconDataSource {

    override fun signInRoom(uuid: String, status: BleBody): Flowable<BleModel> {
        return eyeBeaconApi.signInRoom(uuid, status).map(signInRoomMapper)
    }

    override fun getHistoryList(): Flowable<List<BleEntity>> {
        return dashboardDao.getAllUserData()
    }

    override fun insertHistory(bleEntity: BleEntity) = dashboardDao.insertUserData(bleEntity)

}