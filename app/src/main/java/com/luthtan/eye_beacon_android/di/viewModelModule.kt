package com.luthtan.eye_beacon_android.di

import com.luthtan.eye_beacon_android.features.dashboard.DashboardViewModel
import com.luthtan.eye_beacon_android.features.login.LoginViewModel
import org.koin.dsl.module

val viewModelModule = module {

    single { LoginViewModel() }
    single { DashboardViewModel() }
}