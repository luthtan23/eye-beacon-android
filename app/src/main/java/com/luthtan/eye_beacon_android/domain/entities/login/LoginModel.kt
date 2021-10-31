package com.luthtan.eye_beacon_android.domain.entities.login

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginPage(
    var eyeBle: String,
    var localIP: String,
    var username: String
): Parcelable

data class ErrorLoginForm(
    var eyeBle: String = "",
    var localIP: String = "",
    var username: String = ""
)