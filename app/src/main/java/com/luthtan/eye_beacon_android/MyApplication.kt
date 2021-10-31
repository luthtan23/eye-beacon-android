package com.luthtan.eye_beacon_android

import android.app.Application
import com.luthtan.eye_beacon_android.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin

class MyApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(listOf(coreModule, databaseModule, repoModule, remoteModule, viewModelModule, bluetoothModule))
        }
    }
}