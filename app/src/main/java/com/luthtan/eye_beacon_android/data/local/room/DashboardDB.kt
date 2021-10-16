package com.luthtan.eye_beacon_android.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luthtan.simplebleproject.data.local.room.DashboardDao
import com.luthtan.simplebleproject.domain.entities.dashboard.BleEntity

@Database(
    entities = [BleEntity::class],
    version = 1,
    exportSchema = false
)
abstract class DashboardDB : RoomDatabase() {

    abstract fun dashboardDao(): DashboardDao
}