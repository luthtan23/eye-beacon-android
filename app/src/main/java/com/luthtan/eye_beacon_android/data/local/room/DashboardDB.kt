package com.luthtan.eye_beacon_android.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.luthtan.eye_beacon_android.domain.entities.dashboard.BleEntity

@Database(
    entities = [BleEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(AppTypeConverters::class)
abstract class DashboardDB : RoomDatabase() {

    abstract fun dashboardDao(): DashboardDao
}