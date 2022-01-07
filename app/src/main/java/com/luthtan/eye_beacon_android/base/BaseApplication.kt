package com.luthtan.eye_beacon_android.base

import android.app.Application

abstract class BaseApplication : Application() {
    abstract fun getBaseUrl(): String
}