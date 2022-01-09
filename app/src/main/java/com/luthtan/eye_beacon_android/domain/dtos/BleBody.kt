package com.luthtan.eye_beacon_android.domain.dtos

import com.google.gson.annotations.SerializedName

data class BleBody (
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("isInside")
    val isInside: Boolean
)