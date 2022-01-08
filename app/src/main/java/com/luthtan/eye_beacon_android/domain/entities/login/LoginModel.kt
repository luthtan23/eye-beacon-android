package com.luthtan.eye_beacon_android.domain.entities.login

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginPage(
    var uuid: String,
    var localIP: String,
    var username: String
): Parcelable