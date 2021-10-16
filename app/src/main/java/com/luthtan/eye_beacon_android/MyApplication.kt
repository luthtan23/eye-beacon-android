package com.luthtan.eye_beacon_android

import com.google.android.play.core.splitcompat.SplitCompatApplication
import com.luthtan.eye_beacon_android.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : SplitCompatApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(listOf(coreModule, databaseModule, repoModule, remoteModule, viewModelModule, bluetoothModule))
        }
    }
}