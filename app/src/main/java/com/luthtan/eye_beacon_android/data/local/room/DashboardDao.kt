package com.luthtan.eye_beacon_android.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.luthtan.eye_beacon_android.domain.entities.dashboard.BleEntity
import io.reactivex.Flowable

@Dao
interface DashboardDao {

    @Query("SELECT * from user_ble_table ORDER BY id DESC")
    fun getAllUserData(): Flowable<List<BleEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUserData(bleEntity: BleEntity): Long
}