package com.luthtan.eye_beacon_android.di

import com.luthtan.eye_beacon_android.data.datasource.LocalDataSource
import com.luthtan.eye_beacon_android.data.datasource.RemoteDataSource
import com.luthtan.eye_beacon_android.data.utils.AppExecutors
import org.koin.dsl.module

val remoteModule = module {

    single { RemoteDataSource(get()) }
    single { LocalDataSource(get()) }
    single { AppExecutors() }

}