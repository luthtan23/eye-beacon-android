package com.luthtan.eye_beacon_android

import com.luthtan.eye_beacon_android.base.BaseApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : BaseApplication() {
    override fun getBaseUrl(): String = "https://reqbin.com/"

}