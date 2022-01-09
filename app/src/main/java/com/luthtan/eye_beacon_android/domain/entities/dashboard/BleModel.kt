package com.luthtan.eye_beacon_android.domain.entities.dashboard

data class BleModel(
    val status: StatusModel? = null
)

data class StatusModel(
    val name: String? = null,
    val isInside: Boolean? = null,
    val room: String? = null
)
