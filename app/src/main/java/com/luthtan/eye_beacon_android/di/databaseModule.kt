package com.luthtan.eye_beacon_android.di

import androidx.room.Room
import com.luthtan.eye_beacon_android.data.local.room.DashboardDB
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

const val DATABASE_NAME = "dashboard_db"

val databaseModule = module {

    single {
        Room.databaseBuilder(
            (androidApplication()),
            DashboardDB::class.java,
            DATABASE_NAME
        )
            .build()
    }

    single { (get() as DashboardDB).dashboardDao() }

}