package com.luthtan.eye_beacon_android.domain.dtos

enum class BluetoothState {
    STATE_OFF,
    STATE_TURNING_OFF,
    STATE_ON,
    STATE_TURNING_ON,
}

enum class StateStage {
    STATE_INIT,
    STATE_IN,
    STATE_OUT,
    STATE_IDLE
}