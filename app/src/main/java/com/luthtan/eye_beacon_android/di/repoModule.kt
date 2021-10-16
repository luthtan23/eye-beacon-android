package com.luthtan.eye_beacon_android.di

import com.luthtan.eye_beacon_android.data.repository.dashboard.DashboardRepository
import org.koin.dsl.module

val repoModule = module {

    single { DashboardRepository(get(), get(), get()) }

}